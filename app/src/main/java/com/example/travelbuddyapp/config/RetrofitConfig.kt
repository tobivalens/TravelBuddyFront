package com.example.travelbuddyapp.config
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfig {

    val directusRetrofit:Retrofit = Retrofit.Builder()
            .baseUrl("https://8565-191-156-6-182.ngrok-free.app")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}