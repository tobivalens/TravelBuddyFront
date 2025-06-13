package com.example.travelbuddyapp.datasource.DTOS


data class ActivityData(
    val id_evento: Int,
    val nombre: String,
    val descripcion: String,
    val fecha_actividad: String,
    val hora_actividad: String,
    val ubicacion: String,
    val id_imagen: String?=null

)

data class EditActivityData(
    val nombre: String,
    val descripcion: String,
    val fecha_actividad: String,
    val hora_actividad: String,
    val ubicacion: String
)

data class ActivityDTO(
    val id_actividad: Int,
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



