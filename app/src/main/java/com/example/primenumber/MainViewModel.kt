package com.example.primenumber

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.lang.Exception

const val TAG = "MainViewModel"

class MainViewModel : ViewModel() {

    private var _uiState = MutableStateFlow(MainUiState())

    val uiState: StateFlow<MainUiState>
        get() {
            return _uiState.asStateFlow()
        }

    var targetRange by mutableStateOf("")
        private set

    fun updateTargetRange(targetRange: String) {
        this.targetRange = targetRange
    }

    fun updatePrimeNumbersList() {
        var targetRange = 0

        if (this.targetRange.isNotEmpty()) {
            try {
                targetRange = this.targetRange.toInt()
            } catch (e: Exception) {
                Log.d(TAG, "updatePrimeNumbersList: ${e.message}")
            }
        }

        _uiState.update {
            it.copy(primeNumbers = getPrimesForGivenRange(targetRange))
        }
    }

    private fun getPrimesForGivenRange(targetRange: Int): List<Int> {
        val primeNumbers = mutableListOf<Int>()
        if (targetRange >= 2) {
            for (i in 2..targetRange) {
                if (isPrime(i))
                    primeNumbers.add(i)
            }
        }

        return primeNumbers
    }

    private fun isPrime(num: Int): Boolean {
        if (num == 2) return true

        var isPrime = true

        for (i in 2..num / 2) {
            if (num % i == 0) {
                isPrime = false
                break
            }
        }
        return isPrime
    }

}