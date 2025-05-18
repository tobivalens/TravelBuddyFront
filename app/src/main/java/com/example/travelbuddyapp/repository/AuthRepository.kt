package com.example.travelbuddyapp.repository
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.travelbuddyapp.config.RetrofitConfig
import com.example.travelbuddyapp.datasource.AuthService
import com.example.travelbuddyapp.datasource.EventData
import com.example.travelbuddyapp.datasource.LoginData
import com.example.travelbuddyapp.datasource.RegisterData
import com.example.travelbuddyapp.datasource.RegisterDataExtra
import com.example.travelbuddyapp.datasource.local.LocalDataSourceProvider
import kotlinx.coroutines.flow.firstOrNull
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.UUID

class AuthRepository(
    val authService: AuthService = RetrofitConfig.directusRetrofit.create(AuthService::class.java)
) {

    //Logica
    suspend fun login(loginData: LoginData){
        //Invocar el endpoint de auth
        val response = authService.login(loginData)
        //Almacenar el token
        LocalDataSourceProvider.get().save("accesstoken", response.data.access_token)
    }

    suspend fun getAccessToken() : String? {
        var token = LocalDataSourceProvider.get().load("accesstoken").firstOrNull()
        Log.e(">>>", token.toString())
        return token
    }

    suspend fun getUserId() : String?{
        var id = LocalDataSourceProvider.get().load("userid").firstOrNull()
        return id
    }

    suspend fun getAllUsers() {
        var token = LocalDataSourceProvider.get().load("accesstoken").firstOrNull()

        val response = authService.getAllUsers("Bearer $token")
        response.data.forEach { user ->
            Log.e(">>>", user.email)
        }
    }

    suspend fun register(registerData: RegisterData, phone: String, location:String, birthDate: String): Boolean {
        
        val response = authService.register(registerData)
        return if (response.isSuccessful) {
            val directus_user_id = response.body()?.data?.id
            if (directus_user_id != null) {
                val registerDataExtra = RegisterDataExtra(directus_user_id, phone, location, birthDate)
                val extraResponse = authService.registerAppUser(registerDataExtra)
                LocalDataSourceProvider.get().save("userid", (extraResponse.data.id_usuario).toString())
                true
            } else {
                Log.e("REGISTER", "directus_user_id es null")
                false
            }
        } else {
            val errorBody = response.errorBody()?.string()
            Log.e("REGISTER", "Error al registrar: $errorBody")
            false
        }
    }

    suspend fun createEvent(eventName: String, eventDescription: String){

        var id = getUserId()
        var adminId: Int = id!!.toInt()
        val token = getAccessToken()
        val joinCode = generateJoinCode()
        authService.createEvent("Bearer $token", EventData(
            eventName,
            eventDescription,
            joinCode,
            adminId
        ))

    }

    suspend fun generateJoinCode(longitud: Int = 6): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
        return (1..longitud)
            .map { chars.random() }
            .joinToString("")
    }




}

