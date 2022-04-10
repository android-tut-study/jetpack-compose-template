package com.study.compose.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.study.compose.ui.common.components.AppBackground
import com.study.compose.ui.common.theme.ShrineComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        setContent {
            ShrineComposeTheme {
                AppBackground {
                    Text(text = "Home Screen", style = MaterialTheme.typography.h4)
                }
            }
        }
    }
}