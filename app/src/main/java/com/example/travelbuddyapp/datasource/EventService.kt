package com.example.travelbuddyapp.datasource

import com.example.travelbuddyapp.datasource.DTOS.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface EventService {

    @POST("/items/evento")
    suspend fun createEvent(@Header("Authorization") authorization: String, @Body eventData: EventData): Response<EventResponse>

    @GET("/items/evento")
    suspend fun getAllEvents(@Header("Authorization") authorization: String): Response<GetEventData>

    @GET("items/evento/{id}")
    suspend fun getEventById(@Header("Authorization") authorization: String, @Path("id") id: Int): Response<SingleEventData>

    @PATCH("/items/evento/{id}")
    suspend fun editEvent(@Header("Authorization") authorization: String, @Path("id") id: Int, @Body editEventData: EditEventData)

}