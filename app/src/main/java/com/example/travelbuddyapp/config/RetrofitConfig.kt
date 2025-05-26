package com.example.travelbuddyapp.config
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfig {

    val directusRetrofit:Retrofit = Retrofit.Builder()
            .baseUrl("https://7453-190-130-103-24.ngrok-free.app")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}