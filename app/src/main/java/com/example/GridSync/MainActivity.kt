package com.example.GridSync

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.GridSync.presentation.navigation.AppNavigation
import com.example.GridSync.ui.theme.GridSyncTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GridSyncTheme {
                AppNavigation()
            }
        }
    }
}
