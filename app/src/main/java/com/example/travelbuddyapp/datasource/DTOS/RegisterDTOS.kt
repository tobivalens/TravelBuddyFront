package com.example.travelbuddyapp.datasource.DTOS

data class RegisterData(

    val first_name: String,
    val last_name: String,
    val email: String,
    val password: String,
    val role: String

)

data class RegisterDataExtra(

    val directus_user_id: String,
    val telefono: String,
    val fecha_nacimiento: String,
    val ubicacion: String,

    )

data class RegisterExtraResponse(

    val data: RegisterExtraResponseData
)

data class RegisterExtraResponseData(

    val id_usuario: Int,
    val directus_user_id: String
)

data class RegisterResponse(

    val data: DirectusUserData

)

data class DirectusUserData(

    val id: String,
    val first_name: String,
    val last_name: String,
    val email: String,


    )