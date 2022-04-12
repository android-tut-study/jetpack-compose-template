package com.study.compose.ui.landing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.study.compose.ui.common.components.AppBackground
import com.study.compose.ui.common.theme.ShrineComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LandingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(inflater.context).apply {
        setContent {
            ShrineComposeTheme {
                AppBackground {
                    LandingScreen {
                        findNavController().navigate(com.study.compose.ui.common.R.id.landingToHome)
                    }
                }
            }
        }
    }
}