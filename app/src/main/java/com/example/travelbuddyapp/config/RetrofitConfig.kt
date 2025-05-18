package com.example.travelbuddyapp.config
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfig {

    val directusRetrofit:Retrofit = Retrofit.Builder()
            .baseUrl("https://1f3e-2803-e5e0-2109-9300-ec56-5cf-ef9e-e050.ngrok-free.app")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}