package com.example.travelbuddyapp.config
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfig {

    val directusRetrofit:Retrofit = Retrofit.Builder()
            .baseUrl(" https://7c13-2800-e2-4b80-1136-59bf-5cb6-323c-27b6.ngrok-free.app ")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}