package com.example.travelbuddyapp.datasource

import com.example.travelbuddyapp.datasource.DTOS.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface EventService {

    @POST("/items/evento")
    suspend fun createEvent(@Header("Authorization") authorization: String, @Body eventData: EventData): Response<EventResponse>

    @GET("/items/evento")
    suspend fun getAllEvents(@Header("Authorization") authorization: String): Response<GetEventData>

    @GET("items/evento/{id}")
    suspend fun getEventById(@Header("Authorization") authorization: String, @Path("id") id: Int): Response<SingleEventData>

    @PATCH("/items/evento/{id}")
    suspend fun editEvent(@Header("Authorization") authorization: String, @Path("id") id: Int, @Body editEventData: EditEventData)

    @DELETE("/items/evento/{id}")
    suspend fun deleteEvent(@Header("Authorization") authorization: String, @Path("id") id:Int)

    @GET("/items/evento")
    suspend fun getEventByCode(@Header("Authorization") authorization: String, @Query("filter[codigo_union][_eq]") unionCode: String): Response<GetEventData>

    @POST("/items/participanteevento")
    suspend fun registerParticipation(@Header("Authorization") authorization: String, @Body joinData: JoinData)

    @GET("/items/participanteevento")
    suspend fun getUserParticipations(@Header("Authorization") authorization: String, @Query("filter[id_usuario][_eq]") userId: Int): Response<ParticipationsList>

    @GET("/items/participanteevento")
    suspend fun getParticipants(@Header ("Authorization") authorization: String, @Query("filter[id_evento][_eq]") eventId: Int,
                                @Query("fields") fields: String = "id_usuario.id_usuario,id_usuario.directus_user_id.first_name,id_usuario.directus_user_id.last_name"): Response<DirectusResponse<List<ParticipantName>>>
}