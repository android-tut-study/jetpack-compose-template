package com.study.compose.ui.home.interactor.intent

import androidx.compose.ui.geometry.Offset
import com.study.compose.ui.home.data.Product

sealed class HomeIntent {
    object FetchProducts : HomeIntent()
    data class AddCart(val product: Product, val coordinate: Offset) : HomeIntent()
    object ClearIdProductAdded : HomeIntent()
    data class SelectCategory(val category: String) : HomeIntent()
}