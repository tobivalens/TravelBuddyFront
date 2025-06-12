package com.example.travelbuddyapp.repository

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.travelbuddyapp.config.RetrofitConfig
import com.example.travelbuddyapp.datasource.AuthService
import com.example.travelbuddyapp.datasource.AuxService
import com.example.travelbuddyapp.datasource.DTOS.EditEventData
import com.example.travelbuddyapp.datasource.DTOS.EventData
import com.example.travelbuddyapp.datasource.DTOS.EventResponse
import com.example.travelbuddyapp.datasource.DTOS.JoinData

import com.example.travelbuddyapp.datasource.EventService
import com.google.gson.Gson

class EventsRepository(
    val eventService: EventService = RetrofitConfig.directusRetrofit.create(EventService::class.java),
    val auxRepository: AuxRepository = AuxRepository()
) {



    suspend fun createEvent(eventName: String, eventDescription: String, startDate: String,endDate: String ){

        var id = auxRepository.getUserId()
        var adminId: Int = id!!.toInt()
        val token = auxRepository.getAccessToken()
        val joinCode = generateJoinCode()
        eventService.createEvent("Bearer $token", EventData(
            eventName,
            eventDescription,
            startDate,
            endDate,
            joinCode,
            adminId
        )
        )
    }

    suspend fun editEvent(id: Int, newName: String, newDesc: String, newStart: String, newEnd:String){

        val token = auxRepository.getAccessToken()
        eventService.editEvent(
            "Bearer $token", id,
            EditEventData(
                newName,
                newDesc,
                newStart,
                newEnd)
        )

    }

    suspend fun getAllEvents(): List<EventResponse>? {
        val token = auxRepository.getAccessToken()
        val userId = auxRepository.getUserId()!!.toInt()

        val participationsResponse = eventService.getUserParticipations("Bearer $token", userId)
        val participationData = participationsResponse.body()?.data ?: emptyList()
        val eventResponse = eventService.getAllEvents("Bearer $token")
        if (!eventResponse.isSuccessful) return null

        val events = eventResponse.body()?.data ?: emptyList()

        return events.filter {event -> participationData.any {it.idEvento == event.id_evento || it.idUsuario == event.id_administrador}}
    }

    suspend fun getEventById(id: Int): EventResponse?{
        val token = auxRepository.getAccessToken()
        val response = eventService.getEventById("Bearer $token", id)
        return response.body()?.data
    }

    suspend fun deleteEvent(id: Int){

        val token = auxRepository.getAccessToken()
        eventService.deleteEvent("Bearer $token", id)
    }

    suspend fun joinEvent(unionCode: String){

        val token = auxRepository.getAccessToken()
        val userId = auxRepository.getUserId()!!.toInt()

        Log.e("UNIONCODE: ", unionCode)

        val response = eventService.getEventByCode("Bearer $token", unionCode)

        if (response.isSuccessful && response.body() != null) {
            val eventos = response.body()!!.data
            if (eventos.isNotEmpty()) {
                val eventId = eventos.first().id_evento
                Log.e("tag", "EVENT_ID >>>>: $eventId")
                eventService.registerParticipation("Bearer $token",
                    JoinData(
                        userId,
                        eventId
                    )
                )
            } else {
                Log.e("tag", "No se encontraron eventos para el código: $unionCode")
            }
        } else {
            Log.e("tag", "Error en la respuesta: ${response.code()} - ${response.message()}")
            Log.e("tag", "Response body: ${response.errorBody()?.string()}")
        }


    }

    suspend fun getParticipantsNames(eventId: Int): List<String> {
        val token = auxRepository.getAccessToken()
        val response = eventService.getParticipants("Bearer $token", eventId)

        if (response.isSuccessful) {
            val debugBody = response.body()
            Log.e("RAW JSON", response.errorBody()?.string() ?: "Sin error.")
            val json = Gson().toJson(response.body())
            Log.d("DEBUG_JSON", json)
            Log.e("BODY", "Body: ${debugBody.toString()}")

            return response.body()?.data?.map {
                val user = it.usuario!!.directusUser!!
                val userId = it.usuario.id
                "${user.firstName} ${user.lastName} - ID: ${userId}"
            } ?: emptyList()

        } else {
            throw Exception("Error obteniendo participantes: ${response.code()}")
        }
    }


    fun generateJoinCode(longitud: Int = 6): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        return (1..longitud)
            .map { chars.random() }
            .joinToString("")
    }
}