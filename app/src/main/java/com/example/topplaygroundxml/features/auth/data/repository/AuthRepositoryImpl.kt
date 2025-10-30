package com.example.topplaygroundxml.features.auth.data.repository

import com.example.topplaygroundxml.features.auth.data.local.dao.UserDao
import com.example.topplaygroundxml.features.auth.data.local.model.User
import com.example.topplaygroundxml.features.auth.domain.model.AuthResult
import com.example.topplaygroundxml.features.auth.domain.repository.AuthRepository

class AuthRepositoryImpl(private val userDao: UserDao) : AuthRepository {

    // Simple pseudo-hashing for the example. In a real app, use a strong hashing library like BCrypt.
    private fun hashPassword(password: String): String {
        return "hashed_$password"
    }

    override suspend fun loginUser(email: String, passwordHash: String): AuthResult {
        val user = userDao.findUserByEmail(email)
        return if (user != null && user.passwordHash == hashPassword(passwordHash)) {
            AuthResult.Success
        } else {
            AuthResult.Error("Invalid email or password")
        }
    }

    override suspend fun registerUser(email: String, passwordHash: String): AuthResult {
        if (userDao.findUserByEmail(email) != null) {
            return AuthResult.Error("User with this email already exists")
        }
        val newUser = User(email = email, passwordHash = hashPassword(passwordHash))
        userDao.insertUser(newUser)
        return AuthResult.Success
    }
}