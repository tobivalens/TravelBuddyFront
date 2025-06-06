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


class ExpenseViewModel(val expensesRepository: ExpensesRepository= ExpensesRepository()) : ViewModel() {

    var expenses by mutableStateOf<List<ExpenseDTO>>(emptyList())
        private set

    var total by mutableStateOf(0.0)
        private set
/**
    fun loadExpenses(eventId: String) {
        viewModelScope.launch {
            expenses = expensesRepository.getExpensesByEvent(eventId)
            total = expenses.sumOf { it.amount }
        }
    }
**/
    fun addExpense(eventId: Int, name: String, amount: Double, onSuccess: () -> Unit) {
        viewModelScope.launch {
            expensesRepository.addExpense(eventId,name,amount)
            //loadExpenses(eventId)
            onSuccess()
        }
    }
}
