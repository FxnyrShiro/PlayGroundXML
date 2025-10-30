package com.example.topplaygroundxml.features.auth.domain.model

sealed class AuthResult {
    object Success : AuthResult()
    data class Error(val message: String) : AuthResult()
}
