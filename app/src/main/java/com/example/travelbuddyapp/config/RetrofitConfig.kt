package com.example.travelbuddyapp.config
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfig {

    val directusRetrofit:Retrofit = Retrofit.Builder()
            .baseUrl("https://b25e-2800-e2-4b80-1136-c4f2-9032-3873-8032.ngrok-free.app")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}