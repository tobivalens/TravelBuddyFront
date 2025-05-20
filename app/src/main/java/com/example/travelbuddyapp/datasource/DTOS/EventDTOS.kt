package com.example.travelbuddyapp.datasource.DTOS

data class EventData(

    val nombre: String,
    val descripcion: String,
    val codigo_union: String,
    val id_administrador: Int
)

data class CreateEventResponse(

    val id_evento: String,
    val codigo_union: String

)