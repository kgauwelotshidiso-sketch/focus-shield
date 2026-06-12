package com.focusshield.core

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class ShieldAccessibilityService : AccessibilityService() {

    // 1. YOUR OFFLINE TEXT DATABASE
    // Add any domain or keyword you want to block here
    private val blockedKeywords = listOf(
        "pornhub", "xvideos", "xnxx", "onlyfans", "xxx", "adult"
    )

    // 2. SUPPORTED BROWSERS
    private val browserPackages = listOf(
        "com.android.chrome",
        "org.mozilla.firefox",
        "com.opera.browser",
        "com.microsoft.emmx" // Edge Browser
    )

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return

        // Check if the current app is one of our browsers
        if (browserPackages.contains(event.packageName?.toString())) {
            val rootNode = rootInActiveWindow ?: return
            val urlText = findUrlText(rootNode)
            
            if (urlText != null) {
                checkAndBlock(urlText)
            }
        }
    }

    // Scans the screen to find the browser's address bar (EditText)
    private fun findUrlText(node: AccessibilityNodeInfo?): String? {
        if (node == null) return null
        
        if (node.className == "android.widget.EditText") {
            val text = node.text?.toString()
            if (!text.isNullOrEmpty()) {
                return text.lowercase()
            }
        }
        
        for (i in 0 until node.childCount) {
            val child = node.getChild(i)
            val result = findUrlText(child)
            if (result != null) return result
        }
        return null
    }

    // If a blocked word is found, launch your Reflection/Block screen
    private fun checkAndBlock(url: String) {
        for (keyword in blockedKeywords) {
            if (url.contains(keyword)) {
                Log.d("ShieldService", "Blocked match found: $keyword")
                
                val intent = Intent(this, Class.forName("com.focusshield.MainActivity"))
                // Clear the background tasks and force the app to the front
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.putExtra("SHOW_BLOCK_SCREEN", true)
                startActivity(intent)
                break
            }
        }
    }

    override fun onInterrupt() {}
}
