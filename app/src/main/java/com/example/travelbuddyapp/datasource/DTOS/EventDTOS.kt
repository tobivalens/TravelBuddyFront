package com.example.travelbuddyapp.datasource.DTOS

data class EventData(

    val nombre: String,
    val descripcion: String,
    val fecha_inicio: String,
    val fecha_fin: String,
    val codigo_union: String,
    val id_administrador: Int
)

data class GetEventData(
    val data: List<EventResponse>
)

data class SingleEventData(
    val data: EventResponse
)


data class EventResponse(

    val id_evento: String,
    val nombre: String,
    val descripcion: String,
    val fecha_inicio: String,
    val fecha_fin: String,
    val codigo_union: String,
    val id_administrador: Int

)

data class EditEventData(

    val nombre: String,
    val descripcion: String
)