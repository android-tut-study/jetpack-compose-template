package com.study.compose.ui.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class AppStateViewModel @Inject constructor() : ViewModel() {

    private val _actionChannel = MutableSharedFlow<AppViewAction>(
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = 1,
        replay = 0
    )

    private val _state = MutableStateFlow(AppViewState())
    val state: StateFlow<AppViewState>
        get() = _state

    init {
        val initAppState = AppViewState()
        _actionChannel
            .toAppViewState()
            .scan(initAppState) { vs, change -> change.reduce(vs) }
            .onEach { _state.value = it }
            .launchIn(viewModelScope)
    }


    private fun shouldShowBottomCart(show: Boolean) = flow {
        emit(ShowBottomCart(show))
    }

    suspend fun process(action: AppViewAction) = _actionChannel.emit(action)

    private fun Flow<AppViewAction>.toAppViewState() = merge(
        filterIsInstance<AppViewAction.ShowBottomCart>()
            .flatMapConcat { shouldShowBottomCart(show = it.show) }
    )


}

data class AppViewState(
    val showBottomCart: Boolean = true
)

sealed class UiStateChange : UiStatePartialChange<AppViewState>

data class ShowBottomCart(val show: Boolean) : UiStateChange() {
    override fun reduce(vs: AppViewState): AppViewState {
        return vs.copy(showBottomCart = show)
    }
}

sealed class AppViewAction {
    data class ShowBottomCart(val show: Boolean) : AppViewAction()
}

