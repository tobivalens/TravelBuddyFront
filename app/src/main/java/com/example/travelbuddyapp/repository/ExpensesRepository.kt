package com.example.travelbuddyapp.repository

import com.example.travelbuddyapp.config.RetrofitConfig
import com.example.travelbuddyapp.datasource.DTOS.ExpenseDTO
import com.example.travelbuddyapp.datasource.DTOS.ExpenseData
import com.example.travelbuddyapp.datasource.ExpensesService

class ExpensesRepository (
    val expenseService: ExpensesService = RetrofitConfig.directusRetrofit.create(ExpensesService::class.java),
    val auxRepository: AuxRepository = AuxRepository()
){

    suspend fun addExpense(id_evento: Int, expenseName: String, expenseValue: Double){
        val token = auxRepository.getAccessToken()
        expenseService.addExpense("Bearer $token", ExpenseData(
            id_evento,
            expenseName,
            expenseValue
        )
        )
    }

}
