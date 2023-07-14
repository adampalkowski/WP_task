package com.example.wp_task.Api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiManager{
    val baseUrl = "https://moviesdatabase.p.rapidapi.com/"

    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
    val movieApiService = retrofit.create(ApiService::class.java)
}
