package com.example.travelbuddyapp.repository

import com.example.travelbuddyapp.config.RetrofitConfig
import com.example.travelbuddyapp.datasource.AuthService
import com.example.travelbuddyapp.datasource.AuxService
import com.example.travelbuddyapp.datasource.DTOS.EventData

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

    fun generateJoinCode(longitud: Int = 6): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        return (1..longitud)
            .map { chars.random() }
            .joinToString("")
    }
}