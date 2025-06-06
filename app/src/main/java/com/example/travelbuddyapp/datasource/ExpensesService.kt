package com.example.travelbuddyapp.datasource

import com.example.travelbuddyapp.datasource.DTOS.ExpenseDTO
import com.example.travelbuddyapp.datasource.DTOS.ExpenseData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ExpensesService {

    @GET("expenses/event/{eventId}")
    suspend fun getExpensesByEvent(@Header("Authorization") authorization: String,@Path("eventId") eventId: String): List<ExpenseDTO>

    @POST("expenses")
    suspend fun addExpense(@Header("Authorization") authorization: String, @Body expense: ExpenseData): ExpenseDTO
}