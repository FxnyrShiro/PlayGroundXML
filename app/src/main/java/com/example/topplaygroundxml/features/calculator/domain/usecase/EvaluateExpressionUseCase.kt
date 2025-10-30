package com.example.topplaygroundxml.features.calculator.domain.usecase

import com.example.topplaygroundxml.features.calculator.domain.repository.CalculationRepository

class EvaluateExpressionUseCase(private val calculationRepository: CalculationRepository) {
    operator fun invoke(expression: String): Double {
        return calculationRepository.evaluateExpression(expression)
    }
}