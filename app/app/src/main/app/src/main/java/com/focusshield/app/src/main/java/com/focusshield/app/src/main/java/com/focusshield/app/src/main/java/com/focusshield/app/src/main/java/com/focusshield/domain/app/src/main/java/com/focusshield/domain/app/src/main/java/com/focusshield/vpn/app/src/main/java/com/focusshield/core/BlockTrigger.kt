package com.focusshield.core

object BlockTrigger {

    var lastBlockedDomain: String? = null

    fun trigger(domain: String) {
        lastBlockedDomain = domain
    }
}
