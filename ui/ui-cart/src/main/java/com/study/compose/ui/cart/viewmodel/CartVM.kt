package com.study.compose.ui.cart.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.study.compose.core.domain.model.CartChangeType
import com.study.compose.ui.cart.data.Cart
import com.study.compose.ui.cart.interactor.intent.CartIntent
import com.study.compose.ui.cart.interactor.state.CartUIPartialChange
import com.study.compose.ui.cart.interactor.state.CartViewState
import com.study.compose.ui.cart.interactor.state.CartsChange
import com.study.compose.ui.common.viewmodel.BaseViewModel
import com.study.compose.usecase.carts.CartChangeUseCase
import com.study.compose.usecase.carts.LazyGetCartsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class CartVM @Inject constructor(
    private val cartChangeUseCase: CartChangeUseCase,
    lazyGetCartsUseCase: LazyGetCartsUseCase
) : BaseViewModel<CartIntent>() {

    private val _intentChannel = MutableSharedFlow<CartIntent>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val viewState: StateFlow<CartViewState>
    val cartLazyFlow: Flow<PagingData<Cart>>

    init {
        val initVS = CartViewState.initial()
        viewState = MutableStateFlow(initVS)
        _intentChannel
            .toPartialChange()
            .scan(initVS) { vs, change -> change.reduce(vs) }
            .onEach { viewState.value = it }
            .catch { viewState.value = viewState.value.copy(error = it) }
            .launchIn(viewModelScope)

        cartLazyFlow = lazyGetCartsUseCase(LazyGetCartsUseCase.Param(5))
            .map { pagingData -> pagingData.map { cartDomain -> Cart(cartDomain) } }
            .cachedIn(viewModelScope)
    }

    override suspend fun processIntent(intent: CartIntent) = _intentChannel.emit(intent)

    private fun Flow<CartIntent>.toPartialChange(): Flow<CartUIPartialChange> {
        return merge(
            filterIsInstance<CartIntent.ListenCartChange>()
                .flatMapConcat {
                    cartsChange()
                }
        )
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