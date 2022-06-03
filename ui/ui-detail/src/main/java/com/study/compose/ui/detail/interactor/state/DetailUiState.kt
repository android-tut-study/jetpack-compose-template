package com.study.compose.ui.detail.interactor.state

import com.study.compose.ui.detail.data.ProductDetail
import com.study.compose.ui.state.UiStatePartialChange

data class DetailViewState(
    val loading: Boolean,
    val currentProduct: ProductDetail?,
    val products: List<ProductDetail>,
    val error: Throwable?
) {
    companion object {
        fun init() = DetailViewState(
            currentProduct = null,
            loading = false,
            products = emptyList(),
            error = null
        )
    }
}

sealed interface DetailUIPartialChange: UiStatePartialChange<DetailViewState>

sealed class GetProducts : DetailUIPartialChange {
    override fun reduce(vs: DetailViewState): DetailViewState = when (this) {
        is Data -> vs.copy(products = products)
        is Error -> vs.copy(loading = false, error = err)
        Loading -> vs.copy(loading = true)
    }

    object Loading : GetProducts()
    data class Data(val products: List<ProductDetail>) : GetProducts()
    data class Error(val err: Throwable) : GetProducts()
}

data class CurrentProduct(val current: ProductDetail): DetailUIPartialChange {
    override fun reduce(vs: DetailViewState): DetailViewState {
        return vs.copy(currentProduct = current)
    }
}
