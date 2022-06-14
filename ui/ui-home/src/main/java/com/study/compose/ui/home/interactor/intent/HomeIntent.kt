package com.study.compose.ui.home.interactor.intent

import com.study.compose.ui.home.data.Product

sealed class HomeIntent {
    object FetchProducts: HomeIntent()
    data class AddCart(val product: Product): HomeIntent()
}