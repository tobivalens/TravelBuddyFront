package com.example.travelbuddyapp.repository

import com.example.travelbuddyapp.config.RetrofitConfig
import com.example.travelbuddyapp.datasource.DTOS.EditExpenseData
import com.example.travelbuddyapp.datasource.DTOS.ExpenseData
import com.example.travelbuddyapp.datasource.DTOS.ExpenseDTO
import com.example.travelbuddyapp.datasource.ExpensesService
import com.example.travelbuddyapp.repository.AuxRepository

class ExpensesRepository(
    private val expensesService: ExpensesService = RetrofitConfig.directusRetrofit.create(
        ExpensesService::class.java
    ),
    private val auxRepository: AuxRepository = AuxRepository()
) {
    class ExpensesRepository(
        val expenseService: ExpensesService = RetrofitConfig.directusRetrofit.create(ExpensesService::class.java),
        val auxRepository: AuxRepository = AuxRepository()
    ) {

        /**suspend fun loadExpenses(eventId: Int): List<ExpenseDTO> {
            val token = auxRepository.getAccessToken()
            val response = ExpensesService.getExpenses("Bearer $token", eventId)
            if (response.isSuccessful) {
                return response.body()?.data ?: emptyList()
            }
            throw Exception("Error cargando gastos: ${response.code()} ${response.message()}")
        }**/

        /**
        suspend fun loadExpense(id: Int): ExpenseDTO? {
        val token = auxRepository.getAccessToken()
        val response = expensesService.getExpense("Bearer $token", id)
        return response.body()?.data
        }**/

        /**suspend fun createExpense(id_evento: Int, expenseName: String, generalValue: Double) {
            val token = auxRepository.getAccessToken()
            ExpensesService.createExpense(
                "Bearer $token",
                ExpenseData(id_evento, expenseName, generalValue)
            )
        }**/

        /**suspend fun editExpense(id: Int, newName: String, newValue: Double) {
            suspend fun addExpense(id_evento: Int, expenseName: String, expenseValue: Double) {
                val token = auxRepository.getAccessToken()
                expenseService.addExpense(
                    "Bearer $token", ExpenseData(
                        id_evento,
                        expenseName,
                        expenseValue
                    )
                )
                ExpensesService.editExpense("Bearer $token", id, EditExpenseData(newName, newValue))
            }

            suspend fun deleteExpense(id: Int) {
                val token = auxRepository.getAccessToken()
                ExpensesService.deleteExpense("Bearer $token", id)
            }
        }**/
    }
}