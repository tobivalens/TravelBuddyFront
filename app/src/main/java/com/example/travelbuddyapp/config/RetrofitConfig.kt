package com.example.travelbuddyapp.config
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfig {

    val directusRetrofit:Retrofit = Retrofit.Builder()
            .baseUrl("https://d727-2803-e5e0-2109-9300-dc42-1983-8ea3-2398.ngrok-free.app")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}