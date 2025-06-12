package com.example.travelbuddyapp.repository

import android.util.Log
import com.example.travelbuddyapp.config.RetrofitConfig
import com.example.travelbuddyapp.datasource.DTOS.EditExpenseData
import com.example.travelbuddyapp.datasource.DTOS.ExpenseData
import com.example.travelbuddyapp.datasource.DTOS.ExpenseDTO
import com.example.travelbuddyapp.datasource.ExpensesService
import com.example.travelbuddyapp.repository.AuxRepository

class ExpensesRepository(
    private val expensesService: ExpensesService = RetrofitConfig.directusRetrofit.create(ExpensesService::class.java),
    private val auxRepository: AuxRepository = AuxRepository()
) {

    suspend fun loadExpenses(eventId: Int): List<ExpenseDTO> {
        val token = auxRepository.getAccessToken()
        val response = expensesService.getExpenses("Bearer $token", eventId)
        if (response.isSuccessful) {
            return response.body()?.data ?: emptyList()
        }
        throw Exception("Error cargando gastos: ${response.code()} ${response.message()}")
    }

    suspend fun createExpense(eventId: Int, debtorId: Int, description: String, value: Double): Boolean {
        val token = auxRepository.getAccessToken()
        val userId = auxRepository.getUserId()

        if(userId.isNullOrEmpty()){
           return false
        }
        else {
            val creditorId = userId.toIntOrNull()
            val response = expensesService.createExpense(
                "Bearer $token",
                ExpenseData(
                    eventId,
                    debtorId,
                    creditorId!!,
                    value,
                    description
                )
            )
            return response.isSuccessful
        }


    }

    suspend fun getExpenseById(expenseId: Int): ExpenseDTO? {
        Log.e("expenseId - GetExpenseById - Repository", expenseId.toString())
        val token = auxRepository.getAccessToken()
        val response = expensesService.getExpense("Bearer $token", expenseId)

        if(response.isSuccessful){
            return response.body()?.data
        }
        else{
            throw Exception("Error cargando gasto: ${response.code()} ${response.message()}")
        }
    }

    suspend fun loadUserExpenses(userId: Int): List<ExpenseDTO>{
        val token = auxRepository.getAccessToken()
        val response = expensesService.getUserExpenses("Bearer $token", userId)

        if(response.isSuccessful){
            return response.body()?.data ?: emptyList()
        }
        else{
            throw Exception("Error cargando gasto: ${response.code()} ${response.message()}")
        }

    }

    suspend fun loadUserExpensesInEvent(eventId: Int): List<ExpenseDTO>{
        val token = auxRepository.getAccessToken()
        val userId = auxRepository.getUserId()
        return if(userId.isNullOrEmpty()) {
            emptyList()
        }
        else {
            val userIdInt = userId.toInt()
            val response = expensesService.getUserExpensesInEvent("Bearer $token", userIdInt, eventId)

            if (response.isSuccessful) {
                return response.body()?.data ?: emptyList()
            } else {
                throw Exception("Error cargando gasto: ${response.code()} ${response.message()}")
            }
        }
    }

    suspend fun editExpense(id: Int, debtorId: Int, value: Double, description: String) {
        Log.e("ExpenseID - Repository:", id.toString())
        val token = auxRepository.getAccessToken()
        expensesService.editExpense("Bearer $token", id,
            EditExpenseData(
                debtorId,
                value,
                description,
                )
        )
    }

    suspend fun deleteExpense(id: Int) {
        val token = auxRepository.getAccessToken()
        expensesService.deleteExpense("Bearer $token", id)
    }
}

