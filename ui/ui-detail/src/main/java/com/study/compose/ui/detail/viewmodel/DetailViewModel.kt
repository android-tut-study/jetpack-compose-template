package com.study.compose.ui.detail.viewmodel

import com.study.compose.ui.common.viewmodel.BaseViewModel
import com.study.compose.ui.detail.interactor.intent.DetailIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(): BaseViewModel<DetailIntent>() {
    override suspend fun processIntent(intent: DetailIntent) {

    }
}