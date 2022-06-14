package com.study.compose.ui.home.interactor.state

import com.study.compose.ui.home.data.HomeProduct
import com.study.compose.ui.home.data.Product
import com.study.compose.ui.state.UiStatePartialChange

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

sealed interface HomePartialChange : UiStatePartialChange<HomeViewState>

sealed class FetchProducts : HomePartialChange {
    override fun reduce(vs: HomeViewState): HomeViewState {
        return when (this) {
            is Loading -> vs.copy(loading = true)
            is Data -> vs.copy(loading = false, product = product)
            is Error -> vs.copy(loading = false, error = err)
        }
    }

    object Loading : FetchProducts()
    data class Data(val product: HomeProduct) : FetchProducts()
    data class Error(val err: Throwable) : FetchProducts()
}

sealed class AddCart : HomePartialChange {
    override fun reduce(vs: HomeViewState): HomeViewState = when (this) {
        is Data -> {
            val newProducts = vs.product.products.map {
                if (it.id == product.id) it.copy(isAdded = true) else it
            }
            vs.copy(product = HomeProduct(products = newProducts))
        }
        is Error -> vs.copy(loading = false, error = err)
        Loading -> vs.copy(loading = true)
    }

    object Loading : AddCart()
    data class Data(val product: Product) : AddCart()
    data class Error(val err: Throwable) : AddCart()
}