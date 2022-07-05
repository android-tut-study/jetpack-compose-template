package com.study.compose.ui.home.interactor.state

import com.study.compose.ui.home.data.HomeProduct
import com.study.compose.ui.home.data.Product
import com.study.compose.ui.state.UiStatePartialChange

data class HomeViewState(
    val drawerOpened: Boolean,
    val loading: Boolean,
    val product: HomeProduct,
    val error: Throwable?,
    val idProductAdded: Long?,
    val categories: List<String>,
    val categoryMenuSelected: String? = null
) {
    companion object {
        fun initial() = HomeViewState(
            drawerOpened = false,
            loading = false,
            product = HomeProduct(emptyList()),
            error = null,
            idProductAdded = null,
            categories = emptyList(),
            categoryMenuSelected = null
        )
    }
}

sealed interface HomePartialChange : UiStatePartialChange<HomeViewState>

sealed class FetchProducts : HomePartialChange {
    override fun reduce(vs: HomeViewState): HomeViewState {
        return when (this) {
            is Loading -> vs.copy(loading = true)
            is Data -> vs.copy(
                loading = false,
                product = product,
                categories = product.products.distinctBy { it.category }.map { it.category }
            )
            is Error -> vs.copy(loading = false, error = err)
        }
    }

    object Loading : FetchProducts()
    data class Data(val product: HomeProduct) : FetchProducts()
    data class Error(val err: Throwable) : FetchProducts()
}

sealed class AddCart : HomePartialChange {
    override fun reduce(vs: HomeViewState): HomeViewState = when (this) {
        is Data -> vs.copy(idProductAdded = product.id)
        is Error -> vs.copy(loading = false, error = err)
        Loading -> vs.copy(loading = true)
    }

    object Loading : AddCart()
    data class Data(val product: Product) : AddCart()
    data class Error(val err: Throwable) : AddCart()
}

object ClearIdProductAdded : HomePartialChange {
    override fun reduce(vs: HomeViewState): HomeViewState {
        return vs.copy(idProductAdded = null)
    }
}

sealed class MenuFilter : HomePartialChange {
    override fun reduce(vs: HomeViewState): HomeViewState = when (this) {
        Restore -> vs.copy(categoryMenuSelected = null)
        is Filtered -> vs.copy(
            categoryMenuSelected = category,
            product = vs.product.copy(filteredProducts = filteredProducts)
        )
    }

    object Restore : MenuFilter()
    data class Filtered(val category: String, val filteredProducts: List<Product>) : MenuFilter()
}