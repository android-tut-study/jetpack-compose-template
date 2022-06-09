package com.study.compose.ui.detail.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.study.compose.core.domain.model.CartDomain
import com.study.compose.core.domain.model.ProductDomain
import com.study.compose.ui.common.viewmodel.BaseViewModel
import com.study.compose.ui.detail.data.ProductDetail
import com.study.compose.ui.detail.interactor.intent.DetailIntent
import com.study.compose.ui.detail.interactor.state.*
import com.study.compose.usecase.carts.AddCartUseCase
import com.study.compose.usecase.carts.GetAllCartsUseCase
import com.study.compose.usecase.products.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val addCartUseCase: AddCartUseCase
) : BaseViewModel<DetailIntent>() {

    private val _channel = MutableSharedFlow<DetailIntent>()
    val viewState: StateFlow<DetailViewState>

    init {
        val initVS = DetailViewState.init()
        viewState = MutableStateFlow(initVS)
        _channel
            .distinctUntilChanged { old, new ->
                if (old is DetailIntent.Initial) false else old == new
            }
            .toPartialChange()
            .scan(initVS) { vs, change -> change.reduce(vs) }
            .onEach { viewState.value = it }
            .catch { viewState.value = viewState.value.copy(error = it) }
            .launchIn(viewModelScope)
    }

    override suspend fun processIntent(intent: DetailIntent) = _channel.emit(intent)

    @OptIn(FlowPreview::class)
    private fun Flow<DetailIntent>.toPartialChange(): Flow<DetailUIPartialChange> {
        return merge(
            filterIsInstance<DetailIntent.Initial>()
                .flatMapConcat { products(it.productId) },
            filterIsInstance<DetailIntent.AddCart>()
                .flatMapConcat { addCart(it.product, it.amount) }
                .onStart { emit(AddCart.Loading) }
                .catch { emit(AddCart.Error(it)) }
        )
    }

    private fun products(id: Int): Flow<DetailUIPartialChange> = flow {
        getProductsUseCase()
            .onEach { result ->
                val domainProducts = result.getOrThrow()
                val product = domainProducts.find { it.id == id }
                    ?.let { productDomain -> ProductDetail(productDomain) }
                product?.let { emit(CurrentProduct(it)) }
                val categoryProducts = product?.let { currentProduct ->
                    domainProducts.filter { currentProduct.category == it.category && currentProduct.id != it.id }
                        .map(::ProductDetail)
                }.orEmpty()
                emit(GetProducts.Data(categoryProducts))
            }
            .catch { emit(GetProducts.Error(it)) }
            .collect()
    }

    private suspend fun addCart(product: ProductDetail, amount: Int) = flow<DetailUIPartialChange> {
        val productDomain = product.toDomain()
        val result = addCartUseCase(CartDomain.fromProduct(productDomain, amount))
        emit(AddCart.Data(result.getOrThrow()))
    }
}