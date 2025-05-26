package com.example.travelbuddyapp.config
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitConfig {

    val directusRetrofit:Retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8055")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}