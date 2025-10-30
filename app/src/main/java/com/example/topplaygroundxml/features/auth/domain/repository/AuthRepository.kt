package com.example.topplaygroundxml.features.auth.domain.repository

import com.example.topplaygroundxml.features.auth.domain.model.AuthResult

interface AuthRepository {
    suspend fun loginUser(email: String, passwordHash: String): AuthResult
    suspend fun registerUser(email: String, passwordHash: String): AuthResult
}
