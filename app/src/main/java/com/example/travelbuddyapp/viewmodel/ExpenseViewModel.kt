package com.example.travelbuddyapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelbuddyapp.datasource.DTOS.ExpenseDTO
import com.example.travelbuddyapp.repository.EventsRepository
import com.example.travelbuddyapp.repository.ExpensesRepository
import kotlinx.coroutines.launch

class ExpenseViewModel(
    private val expensesRepository: ExpensesRepository = ExpensesRepository()
) : ViewModel() {

    var expenses by mutableStateOf<List<ExpenseDTO>>(emptyList())
        private set

    var total by mutableStateOf(0.0)
        private set

    fun loadExpenses(eventId: Int) {
        viewModelScope.launch {
            try {
                val loadedExpenses = expensesRepository.loadExpenses(eventId)
                expenses = loadedExpenses
                total = loadedExpenses.sumOf { it.GeneralValue }
            } catch (e: Exception) {
                println("Error al cargar gastos: ${e.message}")
            }
        }
    }

    fun addExpense(eventId: Int, name: String, amount: Double, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                expensesRepository.createExpense(eventId, name, amount)
                loadExpenses(eventId)
                onSuccess()
            } catch (e: Exception) {
                println("Error al añadir gasto: ${e.message}")
            }
        }
    }
}
