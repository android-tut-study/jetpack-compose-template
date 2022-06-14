package com.study.compose.ui.cart.viewmodel

import androidx.lifecycle.viewModelScope
import com.study.compose.core.domain.model.CartChangeType
import com.study.compose.ui.cart.data.Cart
import com.study.compose.ui.cart.interactor.intent.CartIntent
import com.study.compose.ui.cart.interactor.state.CartUIPartialChange
import com.study.compose.ui.cart.interactor.state.CartViewState
import com.study.compose.ui.cart.interactor.state.CartsChange
import com.study.compose.ui.cart.interactor.state.GetCarts
import com.study.compose.ui.common.viewmodel.BaseViewModel
import com.study.compose.usecase.carts.CartChangeUseCase
import com.study.compose.usecase.carts.GetAllCartsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CartVM @Inject constructor(
    private val cartChangeUseCase: CartChangeUseCase,
    private val allCartsUseCase: GetAllCartsUseCase
) : BaseViewModel<CartIntent>() {

    private val _intentChannel = MutableSharedFlow<CartIntent>(extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val viewState: StateFlow<CartViewState>

    init {
        val initVS = CartViewState.initial()
        viewState = MutableStateFlow(initVS)
        _intentChannel
            .toPartialChange()
            .scan(initVS) { vs, change -> change.reduce(vs) }
            .onEach { viewState.value = it }
            .catch { viewState.value = viewState.value.copy(error = it) }
            .launchIn(viewModelScope)
    }

    override suspend fun processIntent(intent: CartIntent) = _intentChannel.emit(intent)

    private fun Flow<CartIntent>.toPartialChange(): Flow<CartUIPartialChange> {
        return merge(
            filterIsInstance<CartIntent.AllCarts>()
                .flatMapConcat {
                    carts()
                },
            filterIsInstance<CartIntent.ListenCartChange>()
                .flatMapConcat {
                    cartsChange()
                }
        )
    }

    fun carts(): Flow<CartUIPartialChange> = flow {
        allCartsUseCase().onEach { result ->
            emit(GetCarts.Data(result.getOrThrow().map { Cart(it) }))
        }.collect()
    }

    fun cartsChange() = flow {
        cartChangeUseCase()
            .onEach { result ->
                val cartChange = result.getOrThrow()
                cartChange.cartDomain?.let { cartDomain ->
                    when (cartChange.type) {
                        CartChangeType.INSERT -> emit(CartsChange.CartAdded(Cart(cartDomain)))
                        CartChangeType.REMOVE -> emit(CartsChange.CartRemoved(Cart(cartDomain)))
                        CartChangeType.UPDATE -> emit(CartsChange.CartUpdated(Cart(cartDomain)))
                        CartChangeType.NONE -> TODO()
                    }
                }
            }
            .collect()
    }

}