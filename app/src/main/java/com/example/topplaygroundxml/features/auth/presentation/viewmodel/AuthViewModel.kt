package com.example.topplaygroundxml.features.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.topplaygroundxml.features.auth.domain.model.AuthResult
import com.example.topplaygroundxml.features.auth.domain.usecase.LoginUserUseCase
import com.example.topplaygroundxml.features.auth.domain.usecase.RegisterUserUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Error(val message: String) : AuthState()
}

sealed class AuthNavigation {
    object ToMainScreen : AuthNavigation()
}

class AuthViewModel(
    private val loginUserUseCase: LoginUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState = _authState.asStateFlow()

    private val _navigation = MutableSharedFlow<AuthNavigation>()
    val navigation = _navigation.asSharedFlow()

    fun login(email: String, passwordHash: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            when (val result = loginUserUseCase(email, passwordHash)) {
                is AuthResult.Success -> _navigation.emit(AuthNavigation.ToMainScreen)
                is AuthResult.Error -> _authState.value = AuthState.Error(result.message)
            }
        }
    }

    fun register(email: String, passwordHash: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            when (val result = registerUserUseCase(email, passwordHash)) {
                is AuthResult.Success -> _navigation.emit(AuthNavigation.ToMainScreen)
                is AuthResult.Error -> _authState.value = AuthState.Error(result.message)
            }
        }
    }
}