package com.paucls.runbookDDD.infrastructure.auth

import org.springframework.stereotype.Component

@Component
class AuthenticationFacade {

    // Hardcoded current user id, spring security is not configured in this demo
    private val currentUserId = "user-id"

    fun getCurrentUserId(): String {
        return currentUserId
    }
}