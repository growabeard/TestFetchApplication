package com.witt.fetchapplication.network

import com.witt.fetchapplication.network.data.Item
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com"

private val retrofit =
    Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(
        BASE_URL
    ).build()

interface ListApiService {
    @GET("/hiring.json")
    suspend fun getItemList(): List<Item>
}

object ListApi {
    val retrofitService: ListApiService by lazy {
        retrofit.create(ListApiService::class.java)
    }
}