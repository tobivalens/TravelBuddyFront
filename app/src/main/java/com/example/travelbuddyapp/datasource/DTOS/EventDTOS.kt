package com.example.travelbuddyapp.datasource.DTOS

import com.google.gson.annotations.SerializedName

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

    val id_evento: Int,
    val nombre: String,
    val descripcion: String,
    val fecha_inicio: String,
    val fecha_fin: String,
    val codigo_union: String,
    val id_administrador: Int

)

data class EditEventData(

    val nombre: String,
    val descripcion: String,
    val fecha_inicio: String,
    val fecha_fin: String
)

data class ParticipationsList(

    val data: List<JoinData>
)

data class JoinData(

    @SerializedName("id_usuario")
    val idUsuario: Int,

    @SerializedName("id_evento")
    val idEvento: Int

)

data class ParticipantName(
    @SerializedName("id_usuario")
    val usuario: UsuarioWrapper?
)

data class UsuarioWrapper(
    @SerializedName("directus_user_id")
    val directusUser: DirectusUser?
)

data class DirectusUser(
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("last_name")
    val lastName: String?
)


data class DirectusResponse<T>(
    val data: T
)