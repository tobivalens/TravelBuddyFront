package com.example.travelbuddyapp.datasource

import com.example.travelbuddyapp.datasource.DTOS.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface EventService {

    @POST("/items/evento")
    suspend fun createEvent(@Header("Authorization") authorization: String, @Body eventData: EventData): CreateEventResponse

}