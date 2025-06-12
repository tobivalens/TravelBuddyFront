package com.example.travelbuddyapp.config
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfig {

    val directusRetrofit:Retrofit = Retrofit.Builder()
            .baseUrl(" https://1347-2800-e2-4b80-1136-9c89-da5f-59fd-c461.ngrok-free.app")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}