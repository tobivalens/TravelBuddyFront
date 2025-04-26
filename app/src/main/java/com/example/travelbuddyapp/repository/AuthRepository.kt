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
import com.example.travelbuddyapp.datasource.LoginData
import com.example.travelbuddyapp.datasource.RegisterData
import com.example.travelbuddyapp.datasource.RegisterDataExtra
import com.example.travelbuddyapp.datasource.local.LocalDataSourceProvider
import kotlinx.coroutines.flow.firstOrNull
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

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

    suspend fun getAllUsers() {
        var token = LocalDataSourceProvider.get().load("accesstoken").firstOrNull()

        val response = authService.getAllUsers("Bearer $token")
        response.data.forEach { user ->
            Log.e(">>>", user.email)
        }
    }

    suspend fun register(registerData: RegisterData, phone: String, location:String, birthDate: String): Boolean {
        val token = LocalDataSourceProvider.get().load("accesstoken").firstOrNull()
        val response = authService.register("Bearer $token", registerData)

        return if (response.isSuccessful) {
            val directus_user_id = response.body()?.data?.id
            if (directus_user_id != null) {
                val registerDataExtra = RegisterDataExtra(directus_user_id, phone, location, birthDate)
                authService.registerAppUser("Bearer $token", registerDataExtra)
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

}

