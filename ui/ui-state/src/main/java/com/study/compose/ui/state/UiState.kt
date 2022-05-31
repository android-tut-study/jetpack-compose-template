package com.study.compose.ui.state

interface UiStatePartialChange<VS> {
    fun reduce(vs: VS): VS
}