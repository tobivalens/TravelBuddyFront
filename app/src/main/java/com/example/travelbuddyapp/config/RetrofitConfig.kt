package com.example.travelbuddyapp.config
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfig {

    val directusRetrofit:Retrofit = Retrofit.Builder()
            .baseUrl("https://a016-2803-e5e0-2109-9300-40d8-3ca3-bc5e-4471.ngrok-free.app")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}