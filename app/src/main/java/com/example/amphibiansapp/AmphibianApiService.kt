package com.example.amphibiansapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com/"

interface AmphibianApiService {
    @GET("amphibians")
    suspend fun getAmphibians(): List<Amphibian>
}

object AmphibianApi {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val retrofitService: AmphibianApiService by lazy {
        retrofit.create(AmphibianApiService::class.java)
    }
}