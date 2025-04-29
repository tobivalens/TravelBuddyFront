package com.example.travelbuddyapp.config
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfig {

    val directusRetrofit:Retrofit = Retrofit.Builder()
            .baseUrl("https://f649-2803-e5e0-2109-9300-c9d9-aa5d-b82d-990e.ngrok-free.app")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}