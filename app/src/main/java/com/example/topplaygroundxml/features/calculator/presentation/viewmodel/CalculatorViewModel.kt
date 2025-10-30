package com.example.topplaygroundxml.features.calculator.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.topplaygroundxml.features.calculator.domain.usecase.EvaluateExpressionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CalculatorViewModel(private val evaluateExpressionUseCase: EvaluateExpressionUseCase) : ViewModel() {

    private val _expression = MutableStateFlow("")
    val expression = _expression.asStateFlow()

    private val _result = MutableStateFlow("")
    val result = _result.asStateFlow()

    fun onButtonClicked(char: String) {
        _expression.value += char
    }

    fun onBackspaceClicked() {
        if (_expression.value.isNotEmpty()) {
            _expression.value = _expression.value.dropLast(1)
        }
    }

    fun onEqualsClicked() {
        viewModelScope.launch {
            try {
                val res = evaluateExpressionUseCase(_expression.value)
                _result.value = res.toString()
            } catch (e: Exception) {
                _result.value = "Error"
            }
        }
    }

    fun onClearClicked() {
        _expression.value = ""
        _result.value = ""
    }
}