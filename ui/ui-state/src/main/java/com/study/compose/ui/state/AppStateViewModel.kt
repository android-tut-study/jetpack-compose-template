package com.study.compose.ui.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan
import javax.inject.Inject

@HiltViewModel
class AppStateViewModel @Inject constructor() : ViewModel() {

    private val _actionChannel = MutableSharedFlow<AppViewAction>(
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
        extraBufferCapacity = 1,
        replay = 0
    )

    private val _state: MutableStateFlow<AppViewState>
    val state: StateFlow<AppViewState>
        get() = _state

    init {
        val initAppState = AppViewState.init()
        _state = MutableStateFlow(initAppState)
        _actionChannel
            .toAppViewState()
            .scan(initAppState) { vs, change -> change.reduce(vs) }
            .onEach { _state.value = it }
            .launchIn(viewModelScope)
    }


    private fun shouldShowBottomCart(show: Boolean) = flow {
        emit(ShowBottomCart(show))
    }

    private fun shouldShowBottomSheet(show: Boolean) = flow {
        emit(ShowBottomSheet(show))
    }

    suspend fun process(action: AppViewAction) = _actionChannel.emit(action)

    private fun Flow<AppViewAction>.toAppViewState() = merge(
        filterIsInstance<AppViewAction.ShowBottomCart>()
            .flatMapConcat { shouldShowBottomCart(show = it.show) },
        filterIsInstance<AppViewAction.ShowBottomSheet>()
            .flatMapConcat { shouldShowBottomSheet(show = it.showed) },
    )
}

data class AppViewState(
    val showBottomCart: Boolean = true,
    val showBottomSheet: Boolean = false,
) {
    companion object {
        fun init() = AppViewState()
    }
}

sealed class UiStateChange : UiStatePartialChange<AppViewState>

data class ShowBottomCart(val show: Boolean) : UiStateChange() {
    override fun reduce(vs: AppViewState): AppViewState {
        return vs.copy(showBottomCart = show)
    }
}

data class ShowBottomSheet(val shown: Boolean) : UiStateChange() {
    override fun reduce(vs: AppViewState): AppViewState =
        vs.copy(showBottomSheet = shown)
}

sealed class AppViewAction {
    data class ShowBottomCart(val show: Boolean) : AppViewAction()
    data class ShowBottomSheet(val showed: Boolean) : AppViewAction()
}

