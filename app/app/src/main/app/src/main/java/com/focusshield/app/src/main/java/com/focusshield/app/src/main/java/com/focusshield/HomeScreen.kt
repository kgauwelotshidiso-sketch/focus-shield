package com.focusshield

import android.app.Activity
import android.content.Intent
import android.net.VpnService
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.focusshield.vpn.ShieldVpnService

@Composable
fun HomeScreen() {
    val context = LocalContext.current

    // This launcher asks the Android system for permission to run a VPN
    val vpnPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = Intent(context, ShieldVpnService::class.java)
            context.startService(intent)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("FOCUS SHIELD", fontSize = 28.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Protection System: READY")
        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = {
                // Check if Android has already granted us VPN permission
                val intent = VpnService.prepare(context)
                if (intent != null) {
                    vpnPermissionLauncher.launch(intent)
                } else {
                    val serviceIntent = Intent(context, ShieldVpnService::class.java)
                    context.startService(serviceIntent)
                }
            },
            modifier = Modifier.fillMaxWidth(0.8f).height(50.dp)
        ) {
            Text("Start Shield (Block example.com)")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedButton(
            onClick = {
                val serviceIntent = Intent(context, ShieldVpnService::class.java).apply {
                    action = "STOP"
                }
                context.startService(serviceIntent)
            },
            modifier = Modifier.fillMaxWidth(0.8f).height(50.dp)
        ) {
            Text("Stop Shield")
        }
    }
}
