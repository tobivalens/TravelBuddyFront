package com.example.travelbuddyapp.config
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfig {

    val directusRetrofit:Retrofit = Retrofit.Builder()
            .baseUrl("https://e671-2803-e5e0-2109-9300-d512-9dcb-3c08-940e.ngrok-free.app")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}