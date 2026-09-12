package com.example.a3

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoanViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoanModel())
    val uiState: StateFlow<LoanModel> = _uiState.asStateFlow()

    fun onPriceChange(newPrice: String) {
        _uiState.update { it.copy(price = newPrice) }
    }

    fun onInterestRateChange(newRate: Float) {
        _uiState.update { it.copy(interestRate = newRate) }
    }

    fun onMonthsChange(newMonths: Float) {
        _uiState.update { it.copy(months = newMonths) }
    }

    fun calculateLoan() {
        val currentState = _uiState.value
        val principal = currentState.price.toDoubleOrNull() ?: 0.0

        if (principal <= 0.0) {
            _uiState.update {
                it.copy(monthlyPayment = 0.0, totalPayment = 0.0, totalInterest = 0.0)
            }
            return
        }

        val rate = currentState.interestRate / 100.0
        val months = currentState.months.toInt()

        val calculatedInterest = principal * rate * months
        val calculatedTotalPayment = principal + calculatedInterest
        val calculatedMonthlyPayment = if (months > 0) calculatedTotalPayment / months else 0.0

        _uiState.update {
            it.copy(
                monthlyPayment = calculatedMonthlyPayment,
                totalPayment = calculatedTotalPayment,
                totalInterest = calculatedInterest
            )
        }
    }
}