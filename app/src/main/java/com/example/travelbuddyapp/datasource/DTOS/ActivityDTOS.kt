package com.example.travelbuddyapp.datasource.DTOS


data class ActivityData(
    val nombre: String,
    val descripcion: String,
    val id_evento: Int
)

data class EditActivityData(
    val nombre: String,
    val descripcion: String
)

data class ActivityDTO(
    val id_actividad: String,
    val nombre: String,
    val descripcion: String,
    val fecha_actividad: String,
    val hora_actividad: String,
    val ubicacion: String,
    val id_imagen: String

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



