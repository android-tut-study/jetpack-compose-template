package com.study.compose.ui.home.interactor.state

data class HomeViewState(
    val drawerOpened: Boolean = false
)

enum class CartUiState {
    Collapsed,
    Expanded,
    Hidden,
}