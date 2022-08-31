package com.assessment.stocksandnews.network

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Streaming

private const val STOCKS_URL = "https://raw.githubusercontent.com/dsancov/TestData/main/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(STOCKS_URL)
    .build()

interface StocksApiService {
    @Streaming
    @GET("stocks.csv")
    suspend fun downloadFile(): Response<ResponseBody>
}

object StocksApi {
    val retrofitService: StocksApiService by lazy { retrofit.create(StocksApiService::class.java) }
}

