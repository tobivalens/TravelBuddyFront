package com.example.travelbuddyapp.datasource
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import java.sql.Date

interface AuthService {

    @POST("/auth/login")
    suspend fun login(@Body loginData: LoginData) : LoginResponse

    @GET("/users")
    suspend fun getAllUsers(@Header("Authorization") authorization:String) : UserResponse

    @POST("/users")
    suspend fun register(@Header("Authorization") authorization: String, @Body registerData: RegisterData): Response<RegisterResponse>

    @POST("/items/travel_buddy_user")
    suspend fun registerAppUser(@Header("Authorization") authorization: String, @Body registerDataExtra: RegisterDataExtra): RegisterExtraResponse
}

data class RegisterData(

    val first_name: String,
    val last_name: String,
    val email: String,
    val password: String

)

data class RegisterDataExtra(

    val directus_user_id: String,
    val telefono: String,
    val fecha_nacimiento: String,
    val ubicacion: String,

)

data class RegisterExtraResponse(

    val id_usuario: Int
)

data class RegisterResponse(

    val data: DirectusUserData

)

data class DirectusUserData(

    val id: String,
    val first_name: String,
    val last_name: String,
    val email: String

)

data class LoginResponse(
    val data: LoginResponseData
)

data class LoginResponseData(
    val access_token:String,
    val refresh_token:String,
)

data class LoginData(
    val email:String,
    val password:String
)

data class UserResponse(
    val data : List<UserDTO>
)

data class UserDTO(
    val first_name:String,
    val last_name:String,
    val email: String
)