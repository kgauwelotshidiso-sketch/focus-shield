package com.focusshield.vpn

import android.net.VpnService
import android.content.Intent
import android.os.ParcelFileDescriptor

class ShieldVpnService : VpnService() {

    private var vpnInterface: ParcelFileDescriptor? = null

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {

        // VPN scaffold only (safe for CI build)
        // Full packet filtering comes later

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()

        vpnInterface?.close()
    }
}
