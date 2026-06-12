package com.focusshield

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.focusshield.core.BlockTrigger

@Composable
fun BlockScreen() {

    val domain = BlockTrigger.lastBlockedDomain ?: "unknown site"

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "⚠ BLOCKED",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Attempted: $domain")

        Spacer(modifier = Modifier.height(12.dp))

        Text("Pause. You are in control.")

        Spacer(modifier = Modifier.height(12.dp))

        Text("I Pause, I Listen, And I Follow My Dreams")
    }
}
