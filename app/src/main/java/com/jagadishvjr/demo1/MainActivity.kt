package com.jagadishvjr.demo1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jagadishvjr.demo1.navigation.AppNavHost
import com.jagadishvjr.demo1.ui.theme.Demo1Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Demo1Theme {
                AppNavHost()
            }
        }
    }
}
