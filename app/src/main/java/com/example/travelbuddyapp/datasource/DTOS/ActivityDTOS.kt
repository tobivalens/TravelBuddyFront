package com.example.travelbuddyapp.datasource.DTOS


data class ActivityData(
    val nombre: String,
    val descripcion: String,
    val id_evento: Int
)

data class EditActivityData(
    val name: String,
    val description: String
)

data class ActivityDTO(
    val id_Actividad: String,
    val nombre: String,
    val descripcion: String
)
data class GetActivitiesData(
    val data: List<ActivityDTO>
)

data class ActivityResponse(
    val data: ActivityDTO
)

data class SingleActivityData(
    val data: ActivityDTO
)



