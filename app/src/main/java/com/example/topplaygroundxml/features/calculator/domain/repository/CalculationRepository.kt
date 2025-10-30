package com.example.topplaygroundxml.features.calculator.domain.repository

interface CalculationRepository {
    fun evaluateExpression(expression: String): Double
}