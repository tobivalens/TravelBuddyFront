package com.example.travelbuddyapp.datasource

import com.example.travelbuddyapp.datasource.DTOS.CurrentUserData
import com.example.travelbuddyapp.datasource.DTOS.TravelBudUserId
import com.example.travelbuddyapp.datasource.DTOS.TravelBuddyUserResponse
import com.example.travelbuddyapp.datasource.DTOS.UserDTO
import com.example.travelbuddyapp.datasource.DTOS.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface AuxService {

    @GET("/users")
    suspend fun getAllUsers(@Header("Authorization") authorization:String) : UserResponse

    @GET("/users/me")
    suspend fun getDirectusUserID(@Header("Authorization") authorization: String): Response<CurrentUserData>

    @GET("items/travel_buddy_user")
    suspend fun getTravelBudId(@Header("Authorization") authorization: String, @Query("filter[directus_user_id][_eq]") directusUserId: String): Response<TravelBuddyUserResponse>
}