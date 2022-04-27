package com.study.compose.ui.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppStateViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(AppViewState())
    val state: StateFlow<AppViewState>
        get() = _state

    private val shouldShowBottomCart = MutableStateFlow(true)

    init {
        viewModelScope.launch {
            shouldShowBottomCart.collect {
                _state.value = AppViewState(it)
            }
        }
    }

    fun shouldShowBottomCart(show: Boolean) {
        shouldShowBottomCart.value = show
    }

}

data class AppViewState(
    val showBottomCart: Boolean = true
)