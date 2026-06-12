package com.focusshield.vpn

import android.content.Intent
import android.net.VpnService
import android.os.ParcelFileDescriptor
import android.util.Log

class ShieldVpnService : VpnService() {
    private var vpnInterface: ParcelFileDescriptor? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == "STOP") {
            stopVpn()
            return START_NOT_STICKY
        }

        startVpn()
        return START_STICKY
    }

    private fun startVpn() {
        if (vpnInterface != null) return

        try {
            val builder = Builder()
            builder.setSession("Focus Shield")
            
            // Set up a virtual local network connection
            builder.addAddress("10.0.0.2", 24)
            
            // BLOCKING LOGIC: Route the IP of our test website into this VPN.
            // Because we do not forward the traffic anywhere, it gets dropped (blocked).
            // We are blocking example.com (IP: 93.184.215.14)
            builder.addRoute("93.184.215.14", 32) 

            vpnInterface = builder.establish()
            Log.d("ShieldVpn", "VPN Started and blocking test IP")
        } catch (e: Exception) {
            Log.e("ShieldVpn", "Failed to start VPN", e)
        }
    }

    private fun stopVpn() {
        try {
            vpnInterface?.close()
            vpnInterface = null
            stopSelf()
            Log.d("ShieldVpn", "VPN Stopped")
        } catch (e: Exception) {
            Log.e("ShieldVpn", "Failed to stop VPN", e)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopVpn()
    }
}
