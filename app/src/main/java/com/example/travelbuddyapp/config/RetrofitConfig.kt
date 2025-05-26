package com.example.travelbuddyapp.config
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfig {

    val directusRetrofit:Retrofit = Retrofit.Builder()
            .baseUrl("https://6340-2803-e5e0-2109-9300-6039-e7ce-bbc4-c205.ngrok-free.app")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}