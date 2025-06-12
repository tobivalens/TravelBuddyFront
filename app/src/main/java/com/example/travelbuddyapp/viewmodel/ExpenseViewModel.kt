package com.example.travelbuddyapp.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.travelbuddyapp.datasource.DTOS.ExpenseDTO
import com.example.travelbuddyapp.repository.EventsRepository
import com.example.travelbuddyapp.repository.ExpensesRepository
import kotlinx.coroutines.launch

class ExpenseViewModel(
    private val expensesRepository: ExpensesRepository = ExpensesRepository()
) : ViewModel() {

    private val _expenses = mutableStateOf<List<ExpenseDTO>>(emptyList())
    val expenses: State<List<ExpenseDTO>> = _expenses
    private val _total = mutableStateOf<Double>(0.0)
    val total: State<Double> = _total
    private val _userEventTotal = mutableStateOf<Double>(0.0)
    val userEventTotal: State<Double> = _userEventTotal
    private val _currentExpense = mutableStateOf<ExpenseDTO?>(null)
    val currentExpense: State<ExpenseDTO?> =  _currentExpense
    private val _createFlag= mutableStateOf<Boolean>(false)
    val createFlag: State<Boolean> = _createFlag
    private val _editFlag= mutableStateOf<Boolean>(false)
    val editFlag: State<Boolean> = _editFlag
    private val _userExpenses = mutableStateOf<List<ExpenseDTO>>(emptyList())
    val userExpenses: State<List<ExpenseDTO>> = _userExpenses
    private val _userTotal = mutableStateOf<Double>(0.0)
    val userTotal: State<Double> = _userTotal

    fun loadExpenses(eventId: Int) {
        viewModelScope.launch {
            try {
                val loadedExpenses = expensesRepository.loadExpenses(eventId)
                _expenses.value = loadedExpenses
                _total.value = loadedExpenses.sumOf { it.monto}
            } catch (e: Exception) {
                println("Error al cargar gastos: ${e.message}")
            }
        }
    }

    fun loadUserExpenses(userId: Int){
        viewModelScope.launch {
            val loadedExpenses = expensesRepository.loadUserExpenses(userId)
            _userExpenses.value = loadedExpenses
            _userTotal.value = loadedExpenses.sumOf{it.monto}
        }

    }

    fun loadExpenseById(expId: Int){

        viewModelScope.launch{
                val loadedExpense = expensesRepository.getExpenseById(expId)
                _currentExpense.value = loadedExpense
        }
    }

    fun loadUserExpensesInEvent(eventId: Int){
        viewModelScope.launch{
            try{
                val userEventExpenses = expensesRepository.loadUserExpensesInEvent(eventId)
                _userEventTotal.value = userEventExpenses.sumOf{it.monto}
            }
            catch(e: Exception){
                println("Error al cargar gastos: ${e.message}")
            }
        }
    }

    fun addExpense(eventId: Int, debtorId: Int, value: Double, description: String) {
        viewModelScope.launch {
            try {
                _createFlag.value = expensesRepository.createExpense(eventId, debtorId, description, value)
                loadExpenses(eventId)
            } catch (e: Exception) {
                println("Error al añadir gasto: ${e.message}")
            }
        }
    }

    fun editExpense(expenseId: Int, debtorId: Int, value: Double, description: String) {
        viewModelScope.launch {
            try {
                _editFlag.value = expensesRepository.editExpense(expenseId, debtorId, value, description)

            } catch (e: Exception) {
                println("Error al añadir gasto: ${e.message}")
            }
        }
    }

    fun setCreateFlag(status: Boolean) {
        _createFlag.value = status
    }

    fun setEditFlag(status: Boolean) {
        _editFlag.value = status
    }

    fun deleteExpense(expenseId: Int){
        viewModelScope.launch {
           try{
               expensesRepository.deleteExpense(expenseId)
           } catch (e: Exception){
               println("Error: ${e.message}")
           }
        }
    }
}
