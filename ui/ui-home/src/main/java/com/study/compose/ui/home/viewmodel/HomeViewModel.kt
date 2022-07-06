package com.study.compose.ui.home.viewmodel

import androidx.compose.animation.core.estimateAnimationDurationMillis
import androidx.lifecycle.viewModelScope
import com.study.compose.core.domain.model.CartChangeType
import com.study.compose.ui.common.viewmodel.BaseViewModel
import com.study.compose.ui.home.components.MENU_ALL
import com.study.compose.ui.home.data.HomeProduct
import com.study.compose.ui.home.data.HomeProduct.Companion.generateFromDomain
import com.study.compose.ui.home.data.Product
import com.study.compose.ui.home.interactor.intent.HomeIntent
import com.study.compose.ui.home.interactor.state.*
import com.study.compose.usecase.carts.AddCartUseCase
import com.study.compose.usecase.carts.CartChangeUseCase
import com.study.compose.usecase.products.FetchProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchProductsUseCase: FetchProductsUseCase,
    private val addCartUseCase: AddCartUseCase,
    private val cartChangeUseCase: CartChangeUseCase,
) : BaseViewModel<HomeIntent>() {

    private val _intentChannel = MutableSharedFlow<HomeIntent>(
        replay = 0,
        extraBufferCapacity = 1,
        BufferOverflow.DROP_OLDEST
    )
    val viewState: StateFlow<HomeViewState>

    init {
        val initVS = HomeViewState.initial()
        viewState = MutableStateFlow(initVS)

        _intentChannel
            .logIntent()
            .toPartialChange()
            .scan(initVS) { vs, change -> change.reduce(vs) }
            .onEach { viewState.value = it }
            .catch { viewState.value = viewState.value.copy(error = it) }
            .launchIn(viewModelScope)

        cartChangeUseCase()
            .onEach { result ->
                val cartChange = result.getOrThrow()
                cartChange.cartDomain?.let { cart ->
                    val type = cartChange.type
                    if (type == CartChangeType.INSERT) {
                        viewState.value = viewState.value.copy(idProductAdded = cart.productId)
                    } else {
                        viewState.value = viewState.value.copy(idProductAdded = null)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    override suspend fun processIntent(intent: HomeIntent) = _intentChannel.emit(intent)

    @OptIn(FlowPreview::class)
    private fun Flow<HomeIntent>.toPartialChange(): Flow<HomePartialChange> {
        return merge(
            filterIsInstance<HomeIntent.FetchProducts>()
                .flatMapConcat { products() },
            filterIsInstance<HomeIntent.AddCart>()
                .flatMapConcat { addCart(it.product) },
            filterIsInstance<HomeIntent.ClearIdProductAdded>()
                .flatMapConcat { clearProductAdded() },
            filterIsInstance<HomeIntent.SelectCategory>()
                .flatMapConcat { filteredProducts(it.category) }
        )
    }

    fun products(): Flow<HomePartialChange> = fetchProducts()
        .scan(HomeProduct(emptyList())) { _, value -> value }
        .map { FetchProducts.Data(it) as HomePartialChange }
        .onStart { emit(FetchProducts.Loading) }
        .catch { emit(FetchProducts.Error(it)) }

    private fun fetchProducts() = flow {
        fetchProductsUseCase().onEach {
            val home = generateFromDomain(it.getOrThrow())
            emit(home)
        }.collect()
    }

    private fun addCart(product: Product) = flow<HomePartialChange> {
        val result = addCartUseCase(product.toCart())
        emit(AddCart.Data(product.copy(isAdded = result.getOrThrow())))
    }

    private fun clearProductAdded() = flow {
        emit(ClearIdProductAdded)
    }

    private fun filteredProducts(category: String) = flow {
        val products = viewState.value.product.products
        if (category == MENU_ALL) {
            emit(MenuFilter.Restore)
        } else {
            val filteredProducts = products.filter { it.category == category }
            emit(MenuFilter.Filtered(category = category, filteredProducts = filteredProducts))
        }
    }

}