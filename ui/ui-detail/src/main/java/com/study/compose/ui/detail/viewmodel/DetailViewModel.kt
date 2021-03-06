package com.study.compose.ui.detail.viewmodel

import androidx.lifecycle.viewModelScope
import com.study.compose.core.domain.model.CartChangeType
import com.study.compose.core.domain.model.CartDomain
import com.study.compose.ui.common.viewmodel.BaseViewModel
import com.study.compose.ui.detail.data.ProductDetail
import com.study.compose.ui.detail.interactor.intent.DetailIntent
import com.study.compose.ui.detail.interactor.state.AddCart
import com.study.compose.ui.detail.interactor.state.ClearIdProductAdded
import com.study.compose.ui.detail.interactor.state.CurrentProduct
import com.study.compose.ui.detail.interactor.state.DetailUIPartialChange
import com.study.compose.ui.detail.interactor.state.DetailViewState
import com.study.compose.ui.detail.interactor.state.GetProducts
import com.study.compose.usecase.carts.AddCartUseCase
import com.study.compose.usecase.carts.CartChangeUseCase
import com.study.compose.usecase.products.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.scan
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val addCartUseCase: AddCartUseCase,
    cartChangeUseCase: CartChangeUseCase
) : BaseViewModel<DetailIntent>() {

    private val _channel = MutableSharedFlow<DetailIntent>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val viewState: StateFlow<DetailViewState>

    init {
        val initVS = DetailViewState.init()
        viewState = MutableStateFlow(initVS)
        _channel
            .logIntent()
            .toPartialChange()
            .scan(initVS) { vs, change -> change.reduce(vs) }
            .onEach {
                viewState.value = it
            }
            .catch { viewState.value = viewState.value.copy(error = it) }
            .launchIn(viewModelScope)

        cartChangeUseCase()
            .onEach { result ->
                val cartChange = result.getOrThrow()
                cartChange.cartDomain?.let { _ ->
                    val type = cartChange.type
                    viewState.value =
                        viewState.value.copy(addedToCart = type == CartChangeType.INSERT)
                }
            }
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
                .catch { emit(AddCart.Error(it)) },
            filterIsInstance<DetailIntent.ClearCartAddedFlag>()
                .flatMapConcat { clearAddedFlag() }
        )
    }

    private fun products(id: Long): Flow<DetailUIPartialChange> = flow {
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

    private fun addCart(product: ProductDetail, amount: Int) = flow<DetailUIPartialChange> {
        val productDomain = product.toDomain()
        val result = addCartUseCase(CartDomain.fromProduct(productDomain, amount))
        emit(AddCart.Data(result.getOrThrow()))
    }

    private fun clearAddedFlag() = flow {
        emit(ClearIdProductAdded)
    }
}