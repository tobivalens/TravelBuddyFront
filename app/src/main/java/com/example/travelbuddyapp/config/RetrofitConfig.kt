package com.example.travelbuddyapp.config
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfig {

    val directusRetrofit:Retrofit = Retrofit.Builder()
            .baseUrl("https://0220-2803-e5e0-2109-9300-b1e4-8b5c-ad1c-64d0.ngrok-free.app")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}