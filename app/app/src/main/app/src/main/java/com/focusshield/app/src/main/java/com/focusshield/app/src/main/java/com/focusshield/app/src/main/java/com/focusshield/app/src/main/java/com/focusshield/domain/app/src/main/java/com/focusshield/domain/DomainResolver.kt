package com.focusshield.domain

object DomainResolver {

    fun extractDomain(url: String): String {

        return try {

            url
                .lowercase()
                .replace("https://", "")
                .replace("http://", "")
                .split("/")[0]

        } catch (e: Exception) {

            url
        }
    }
}
