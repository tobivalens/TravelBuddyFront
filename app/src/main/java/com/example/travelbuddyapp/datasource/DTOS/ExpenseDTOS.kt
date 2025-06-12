package com.example.travelbuddyapp.datasource.DTOS


data class ExpenseData(
    val id_evento: Int,
    val deudor_id: Int,
    val acreedor_id: Int,
    val monto: Double,
    val descripcion: String,
)

data class EditExpenseData(
    val deudor_id: Int,
    val monto: Double,
    val descripcion: String
)


data class ExpenseDTO(
    val id_gasto: Int,
    val id_evento: Int,
    val deudor_id: Int,
    val acreedor_id: Int,
    val monto: Double,
    val descripcion: String
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
