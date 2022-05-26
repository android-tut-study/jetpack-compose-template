package com.study.compose.ui.home.interactor.intent

sealed class HomeIntent {
    object Initial : HomeIntent()
    object FetchProducts: HomeIntent()
}