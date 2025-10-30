package com.example.topplaygroundxml.features.auth.domain.usecase

import com.example.topplaygroundxml.features.auth.domain.repository.AuthRepository

class RegisterUserUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, passwordHash: String) = authRepository.registerUser(email, passwordHash)
}