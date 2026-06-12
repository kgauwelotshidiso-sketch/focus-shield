package com.focusshield

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val showBlockScreen = intent.getBooleanExtra("SHOW_BLOCK_SCREEN", false)
        
        setContent {
            var isBlocked by remember { mutableStateOf(showBlockScreen) }

            if (isBlocked) {
                BlockScreen(
                    onClose = { finish() },
                    onReadGoals = { /* We will wire this up later */ },
                    onFocusExercise = { /* We will wire this up later */ }
                )
            } else {
                HomeScreen()
            }
        }
    }

    // This catches the emergency signal if the app is already running in the background
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.getBooleanExtra("SHOW_BLOCK_SCREEN", false)) {
            setContent {
                BlockScreen(
                    onClose = { finish() },
                    onReadGoals = { },
                    onFocusExercise = { }
                )
            }
        }
    }
}
