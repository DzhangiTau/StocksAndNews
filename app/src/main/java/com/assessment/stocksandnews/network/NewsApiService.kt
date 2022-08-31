package com.assessment.stocksandnews.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val NEWS_URL = "https://saurav.tech/NewsAPI/everything/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(NEWS_URL)
    .build()

interface NewsApiService {
    @GET("cnn.json")
    suspend fun getNews(): News
}

object NewsApi {
    val retrofitService: NewsApiService by lazy { retrofit.create(NewsApiService::class.java) }
}
