package com.example.travelbuddyapp.datasource.DTOS


data class ExpenseData(
    val id_evento: Int,
    val expenseName: String,
    var GeneralValue: Double,
)

data class EditExpenseData(
    val expenseName: String,
    var GeneralValue: Double
)


data class ExpenseDTO(
    val id: Int,
    val id_evento: Int,
    val expenseName: String,
    var GeneralValue: Double,
)


data class GetExpensesData(
    val data: List<ExpenseDTO>
)

data class SingleExpenseData(
    val data: ActivityDTO
)


data class SingleExpenseResponse(
    val data: ExpenseDTO
)
