package com.example.travelbuddyapp.datasource.DTOS

data class LoginResponse(
    val data: LoginResponseData
)

data class LoginResponseData(
    val access_token:String,
    val refresh_token:String,
)

data class LoginData(
    val email:String,
    val password:String,
)

data class UserResponse(
    val data : List<UserDTO>
)

data class UserDTO(
    val first_name:String,
    val last_name:String,
    val email: String
)

data class CurrentUserData(
    val data: UserID
)

data class UserID(
    val id: String
)

data class TravelBuddyUserResponse(
    val data: List<TravelBudUserId>
)

data class TravelBudUserId(
    val id_usuario: Int,
    val directus_user_id: String,
    val telefono: String,
    val fecha_nacimiento: String,
    val ubicacion: String
)
