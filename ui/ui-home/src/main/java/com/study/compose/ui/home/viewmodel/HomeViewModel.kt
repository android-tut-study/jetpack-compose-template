package com.study.compose.ui.home.viewmodel

import com.study.compose.ui.common.viewmodel.BaseViewModel
import com.study.compose.ui.home.interactor.intent.HomeIntent
import com.study.compose.usecase.products.FetchProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchProductsUseCase: FetchProductsUseCase
) : BaseViewModel<HomeIntent>() {

    override suspend fun processIntent(intent: HomeIntent) {
        TODO("Not yet implemented")
    }
}