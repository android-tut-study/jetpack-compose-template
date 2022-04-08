package com.study.compose.shrine

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.DrawerValue
import androidx.compose.material.rememberDrawerState
import com.study.compose.shrine.components.ShrineScaffold
import androidx.compose.ui.viewinterop.AndroidViewBinding
import com.study.compose.shrine.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            ShrineScaffold(
                drawerState = drawerState
            ) {
                AndroidViewBinding(factory = ActivityMainBinding::inflate)
            }
        }
    }
}