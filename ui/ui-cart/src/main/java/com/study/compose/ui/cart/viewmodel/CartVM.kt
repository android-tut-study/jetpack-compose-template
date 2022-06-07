package com.study.compose.ui.cart.viewmodel

import androidx.lifecycle.viewModelScope
import com.study.compose.ui.cart.interactor.intent.CartIntent
import com.study.compose.ui.cart.interactor.state.CartUIPartialChange
import com.study.compose.ui.cart.interactor.state.CartViewState
import com.study.compose.ui.common.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.*

class CartVM : BaseViewModel<CartIntent>() {

    private val _intentChannel = MutableSharedFlow<CartIntent>()
    val viewState: StateFlow<CartViewState>

    init {
        val initVS = CartViewState.initial()
        viewState = MutableStateFlow(initVS)
        _intentChannel
            .distinctUntilChanged { old, new ->
                if (old == CartIntent.Initial) false else old == new
            }
            .toPartialChange()
            .scan(initVS) {vs, change -> change.reduce(vs)}
            .onEach { viewState.value = it }
            .catch { viewState.value = viewState.value.copy(error = it) }
            .launchIn(viewModelScope)
    }

    override suspend fun processIntent(intent: CartIntent) = _intentChannel.emit(intent)

    private fun Flow<CartIntent>.toPartialChange(): Flow<CartUIPartialChange> {
        return merge(
            filterIsInstance<CartIntent.AllCarts>().flatMapConcat {
                carts()
            }
        )
    }

    fun carts(): Flow<CartUIPartialChange> = flow {}
}