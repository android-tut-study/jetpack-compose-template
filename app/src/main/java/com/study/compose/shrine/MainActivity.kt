package com.study.compose.shrine

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.study.compose.shrine.navigation.AppNavigation
import com.study.compose.ui.common.theme.ShrineComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppContent()
        }
    }

    @Composable
    fun AppContent() {
        val navController = rememberNavController()
        ShrineComposeTheme {
            AppNavigation(
                navController = navController
            )
        }
    }
}