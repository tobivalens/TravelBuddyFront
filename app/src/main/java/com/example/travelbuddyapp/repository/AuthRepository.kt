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
import com.example.travelbuddyapp.datasource.DTOS.*
import com.example.travelbuddyapp.datasource.local.LocalDataSourceProvider
import kotlinx.coroutines.flow.firstOrNull
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.UUID

class AuthRepository(
    val authService: AuthService = RetrofitConfig.directusRetrofit.create(AuthService::class.java)
) {
    suspend fun login(loginData: LoginData){
        val response = authService.login(loginData)
        LocalDataSourceProvider.get().save("accesstoken", response.data.access_token)
    }

    suspend fun register(registerData: RegisterData, phone: String, location:String, birthDate: String): Boolean {
        
        val response = authService.register(registerData)
        return if (response.isSuccessful) {
            val directus_user_id = response.body()?.data?.id
            if (directus_user_id != null) {
                val registerDataExtra = RegisterDataExtra(directus_user_id, phone, location, birthDate)
                authService.registerAppUser(registerDataExtra)
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

    suspend fun clearDataStorage(){
        LocalDataSourceProvider.get().clearKey("accesstoken")
        LocalDataSourceProvider.get().clearKey("userId")
        LocalDataSourceProvider.get().clearKey("username")
    }
}

