package com.example.travelbuddyapp.repository

import android.util.Log
import com.example.travelbuddyapp.config.RetrofitConfig
import com.example.travelbuddyapp.datasource.AuthService
import com.example.travelbuddyapp.datasource.AuxService
import com.example.travelbuddyapp.datasource.DTOS.UserDTO
import com.example.travelbuddyapp.datasource.local.LocalDataSourceProvider
import kotlinx.coroutines.flow.firstOrNull

class AuxRepository(
    val auxService: AuxService = RetrofitConfig.directusRetrofit.create(AuxService::class.java)
){

    suspend fun getAccessToken() : String? {
        var token = LocalDataSourceProvider.get().load("accesstoken").firstOrNull()
        Log.e(">>>", token.toString())
        return token
    }

    suspend fun getUserId() : String?{
        var id = LocalDataSourceProvider.get().load("userId").firstOrNull()
        return id
    }

    suspend fun getAllUsers() {
        var token = LocalDataSourceProvider.get().load("accesstoken").firstOrNull()

        val response = auxService.getAllUsers("Bearer $token")
        response.data.forEach { user ->
            Log.e(">>>", user.email)
        }
    }

    suspend fun getUsername(): String?{

        var name = LocalDataSourceProvider.get().load("username").firstOrNull()

        return name
    }

    suspend fun storeCurrentUserId(){

        var token = getAccessToken()
        val currentUserData = auxService.getDirectusUserID("Bearer $token")
        val directusUserId = currentUserData.body()?.data?.id!!
        Log.e("directusUserId", directusUserId)
        val userName = "${currentUserData.body()?.data?.first_name!!} ${currentUserData.body()?.data?.last_name!!}"

        LocalDataSourceProvider.get().save("username", userName)

        val travelBudData = auxService.getTravelBudId("Bearer $token", directusUserId)
        Log.e("DATA", travelBudData.body().toString())

        val userId = travelBudData.body()?.data?.firstOrNull()?.id_usuario
        Log.e("user id", userId.toString())

        LocalDataSourceProvider.get().save("userId", userId.toString())

    }

}