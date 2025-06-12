package com.example.travelbuddyapp.config
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfig {

    val directusRetrofit:Retrofit = Retrofit.Builder()
            .baseUrl("https://f453-2803-e5e0-2109-9300-59a5-b277-58ec-267a.ngrok-free.app")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}