package com.example.travelbuddyapp.datasource

import com.example.travelbuddyapp.datasource.DTOS.EditExpenseData
import com.example.travelbuddyapp.datasource.DTOS.EventResponse
import com.example.travelbuddyapp.datasource.DTOS.ExpenseDTO

import com.example.travelbuddyapp.datasource.DTOS.ExpenseData
import com.example.travelbuddyapp.datasource.DTOS.GetExpensesData
import retrofit2.Response

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
interface ExpensesService {

    @POST("/items/gasto")
    suspend fun createExpense(
        @Header("Authorization") authorization: String,
        @Body expenseData: ExpenseData
    ): Response<EventResponse>

    @PATCH("/items/gasto/{id}")
    suspend fun editExpense(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int,
        @Body editExpenseData: EditExpenseData
    ): Response<ExpenseDTO>

    @GET("/items/gasto")
    suspend fun getExpenses(
        @Header("Authorization") authorization: String,
        @Query("filter[id_evento][_eq]") idEvento: Int
    ): Response<GetExpensesData>

    @GET("/items/gasto/{id}")
    suspend fun getExpense(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int
    ): Response<ExpenseDTO>

    @DELETE("/items/gasto/{id}")
    suspend fun deleteExpense(
        @Header("Authorization") authorization: String,
        @Path("id") id: Int
    ): Response<Unit>
}
