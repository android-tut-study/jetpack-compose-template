package com.study.compose.ui.home.interactor.state

import com.study.compose.ui.home.data.HomeProduct

data class HomeViewState(
    val drawerOpened: Boolean,
    val loading: Boolean,
    val product: HomeProduct,
    val error: Throwable?

) {
    companion object {
        fun initial() = HomeViewState(
            drawerOpened = false,
            loading = false,
            product = HomeProduct(emptyList()),
            error = null
        )
    }
}

sealed interface HomePartialChange {
    fun reduce(viewState: HomeViewState): HomeViewState
}

sealed class FetchProducts : HomePartialChange {
    override fun reduce(viewState: HomeViewState): HomeViewState {
        return when (this) {
            is Loading -> viewState.copy(loading = true)
            is Data -> viewState.copy(loading = false, product = product)
            is Error -> viewState.copy(loading = false, error = err)
        }
    }

    object Loading : FetchProducts()
    data class Data(val product: HomeProduct) : FetchProducts()
    data class Error(val err: Throwable) : FetchProducts()
}

enum class CartUiState {
    Collapsed,
    Expanded,
    Hidden,
}