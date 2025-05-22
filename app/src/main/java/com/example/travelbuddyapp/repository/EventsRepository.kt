package com.example.travelbuddyapp.repository

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.travelbuddyapp.config.RetrofitConfig
import com.example.travelbuddyapp.datasource.AuthService
import com.example.travelbuddyapp.datasource.AuxService
import com.example.travelbuddyapp.datasource.DTOS.EditEventData
import com.example.travelbuddyapp.datasource.DTOS.EventData
import com.example.travelbuddyapp.datasource.DTOS.EventResponse

import com.example.travelbuddyapp.datasource.EventService

class EventsRepository(
    val eventService: EventService = RetrofitConfig.directusRetrofit.create(EventService::class.java),
    val auxRepository: AuxRepository = AuxRepository()
) {


    suspend fun createEvent(eventName: String, eventDescription: String){

        var id = auxRepository.getUserId()
        var adminId: Int = id!!.toInt()
        val token = auxRepository.getAccessToken()
        val joinCode = generateJoinCode()
        eventService.createEvent("Bearer $token", EventData(
            eventName,
            eventDescription,
            joinCode,
            adminId
        )
        )
    }

    suspend fun editEvent(id: Int, newName: String, newDesc: String){

        val token = auxRepository.getAccessToken()
        eventService.editEvent(
            "Bearer $token", id,
            EditEventData(newName, newDesc)
        )

    }

    suspend fun getAllEvents(): List<EventResponse>?{

        val token = auxRepository.getAccessToken()
        val response = eventService.getAllEvents("Bearer $token")

        return if(response.isSuccessful){
            response.body()?.data
        }
        else{
            null
        }

    }

    suspend fun getEventById(id: Int): EventResponse?{
        val token = auxRepository.getAccessToken()
        val response = eventService.getEventById("Bearer $token", id)
        return response.body()?.data
    }


    fun generateJoinCode(longitud: Int = 6): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        return (1..longitud)
            .map { chars.random() }
            .joinToString("")
    }
}