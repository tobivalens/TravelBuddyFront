package com.example.travelbuddyapp.datasource
import com.example.travelbuddyapp.datasource.DTOS.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {

    @POST("/auth/login")
    suspend fun login(@Body loginData: LoginData) : LoginResponse

    @POST("/users")
    suspend fun register(@Body registerData: RegisterData): Response<RegisterResponse>

    @POST("/items/travel_buddy_user")
    suspend fun registerAppUser(@Body registerDataExtra: RegisterDataExtra): RegisterExtraResponse

}







