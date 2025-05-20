package com.example.travelbuddyapp.datasource

import com.example.travelbuddyapp.datasource.DTOS.UserResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface AuxService {

    @GET("/users")
    suspend fun getAllUsers(@Header("Authorization") authorization:String) : UserResponse

}