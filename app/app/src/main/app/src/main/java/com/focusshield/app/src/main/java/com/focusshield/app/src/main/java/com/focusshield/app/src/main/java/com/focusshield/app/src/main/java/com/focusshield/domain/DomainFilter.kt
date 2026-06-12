package com.focusshield.domain

object DomainFilter {

    private val blockedDomains = mutableSetOf(
        "porn",
        "xxx",
        "adult",
        "sex",
        "xnxx",
        "xvideos"
    )

    fun isBlocked(domain: String): Boolean {

        val clean = domain.lowercase()

        return blockedDomains.any {
            clean.contains(it)
        }
    }

    fun addCustomDomain(domain: String) {
        blockedDomains.add(domain.lowercase())
    }
}
