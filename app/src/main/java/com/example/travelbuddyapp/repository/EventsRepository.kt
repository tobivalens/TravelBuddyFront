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


    suspend fun createEvent(
        eventName: String,
        eventDescription: String,
        startDate: String,
        endDate: String
    ): Boolean{

        var id = auxRepository.getUserId()
        var adminId: Int = id!!.toInt()
        val token = auxRepository.getAccessToken()
        val joinCode = generateJoinCode()
        val response =
            eventService.createEvent(
            "Bearer $token", EventData(
                eventName,
                eventDescription,
                startDate,
                endDate,
                joinCode,
                adminId
            )
        )

        return response.isSuccessful
    }

    suspend fun editEvent(
        id: Int,
        newName: String,
        newDesc: String,
        newStart: String,
        newEnd: String
    ): Boolean {

        val token = auxRepository.getAccessToken()
        val response =
            eventService.editEvent(
            "Bearer $token", id,
            EditEventData(
                newName,
                newDesc,
                newStart,
                newEnd
            )
        )

        return response.isSuccessful
    }

    suspend fun getAllEvents(): List<EventResponse>? {
        val token = auxRepository.getAccessToken()
        val userId = auxRepository.getUserId()?.toIntOrNull() ?: return null

        val participationsResponse = eventService.getUserParticipations("Bearer $token", userId)
        val participationData = participationsResponse.body()?.data ?: emptyList()

        val eventResponse = eventService.getAllEvents("Bearer $token")
        if (!eventResponse.isSuccessful) return null

        val allEvents = eventResponse.body()?.data ?: emptyList()


        val adminEvents = allEvents.filter { it.id_administrador == userId }


        val participantEventIds = participationData.map { it.idEvento }
        val participantEvents = allEvents.filter { it.id_evento in participantEventIds }


        return (adminEvents + participantEvents).distinctBy { it.id_evento }
    }

    suspend fun getParticipatedEvents(): List<EventResponse>? {

        val token = auxRepository.getAccessToken()
        val userId = auxRepository.getUserId()?.toIntOrNull() ?: return null

        val participationsResponse = eventService.getUserParticipations("Bearer $token", userId)
        val participationData = participationsResponse.body()?.data ?: emptyList()

        val eventResponse = eventService.getAllEvents("Bearer $token")
        if (!eventResponse.isSuccessful) return null

        val allEvents = eventResponse.body()?.data ?: emptyList()

        val participantEventIds = participationData.map { it.idEvento }
        val participantEvents = allEvents.filter { it.id_evento in participantEventIds }

        return participantEvents
    }

    suspend fun getOwnedEvents(): List<EventResponse>?{

        val token = auxRepository.getAccessToken()
        val userId = auxRepository.getUserId()?.toIntOrNull() ?: return null

        val eventResponse = eventService.getAllEvents("Bearer $token")
        if (!eventResponse.isSuccessful) return null

        val allEvents = eventResponse.body()?.data ?: emptyList()

        val adminEvents = allEvents.filter { it.id_administrador == userId }

        return adminEvents

    }




    suspend fun getEventById(id: Int): EventResponse? {
        val token = auxRepository.getAccessToken()
        val response = eventService.getEventById("Bearer $token", id)


        return if(response.isSuccessful){
            response.body()?.data
        }
        else{
            return null
        }
    }

    suspend fun deleteEvent(id: Int) {

        val token = auxRepository.getAccessToken()
        eventService.deleteEvent("Bearer $token", id)
    }

    suspend fun joinEvent(unionCode: String): Boolean{

        val token = auxRepository.getAccessToken()
        val userId = auxRepository.getUserId()!!.toInt()
        val response = eventService.getEventByCode("Bearer $token", unionCode)

        if (response.isSuccessful && response.body() != null) {
            val eventos = response.body()!!.data
            if (eventos.isNotEmpty()) {
                val eventId = eventos.first().id_evento
                eventService.registerParticipation(
                    "Bearer $token",
                    JoinData(
                        userId,
                        eventId
                    )
                )
                return true
            } else {
                Log.e("tag", "No se encontraron eventos para el código: $unionCode")
                return false
            }
        } else {
            Log.e("tag", "Error en la respuesta: ${response.code()} - ${response.message()}")
            Log.e("tag", "Response body: ${response.errorBody()?.string()}")
            return false
        }


    }

    suspend fun getParticipantsNames(eventId: Int): List<String> {
        val token = auxRepository.getAccessToken()
        val response = eventService.getParticipants("Bearer $token", eventId)

        if (response.isSuccessful) {
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