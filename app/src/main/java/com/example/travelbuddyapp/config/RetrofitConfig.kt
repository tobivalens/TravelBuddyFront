package com.example.travelbuddyapp.config
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfig {

    val directusRetrofit:Retrofit = Retrofit.Builder()
            .baseUrl("https://e2e6-2803-e5e0-2109-9300-e8ee-ad1f-5b51-d20b.ngrok-free.app")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}