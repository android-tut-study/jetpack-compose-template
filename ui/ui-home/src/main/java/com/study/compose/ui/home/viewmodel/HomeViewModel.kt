package com.study.compose.ui.home.viewmodel

import androidx.lifecycle.viewModelScope
import com.study.compose.ui.common.viewmodel.BaseViewModel
import com.study.compose.ui.home.data.HomeProduct
import com.study.compose.ui.home.data.HomeProduct.Companion.generateFromDomain
import com.study.compose.ui.home.interactor.intent.HomeIntent
import com.study.compose.ui.home.interactor.state.FetchProducts
import com.study.compose.ui.home.interactor.state.HomePartialChange
import com.study.compose.ui.home.interactor.state.HomeViewState
import com.study.compose.usecase.products.FetchProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchProductsUseCase: FetchProductsUseCase
) : BaseViewModel<HomeIntent>() {

    private val _intentChannel = MutableSharedFlow<HomeIntent>()
    val viewState: StateFlow<HomeViewState>

    init {
        val initVS = HomeViewState.initial()
        viewState = MutableStateFlow(initVS)

        _intentChannel
            .distinctUntilChanged { old, new ->
                if (old == HomeIntent.Initial) false else old == new
            }
            .toPartialChange()
            .scan(initVS) { vs, change -> change.reduce(vs) }
            .onEach { viewState.value = it }
            .catch { viewState.value = viewState.value.copy(error = it) }
            .launchIn(viewModelScope)
    }

    override suspend fun processIntent(intent: HomeIntent) = _intentChannel.emit(intent)

    @OptIn(FlowPreview::class)
    private fun Flow<HomeIntent>.toPartialChange(): Flow<HomePartialChange> {
        return merge(
            filterIsInstance<HomeIntent.FetchProducts>()
                .flatMapConcat { products() }
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
}