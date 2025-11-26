package com.mangelos.mangelos.network

import android.content.Context
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    // ¡¡¡IMPORTANTE: CAMBIA ESTO POR TU URL DE XANO!!!
    private const val BASE_URL = "https://x8ki-letl-twmt.n7.xano.io/api:p8gz43yf/"

    @Volatile
    private var instance: ApiService? = null

    fun getInstance(context: Context): ApiService {
        return instance ?: synchronized(this) {
            instance ?: buildRetrofit(context).also { instance = it }
        }
    }

    private fun buildRetrofit(context: Context): ApiService {
        // 1. Creamos el chismoso
        val logging = okhttp3.logging.HttpLoggingInterceptor()
        logging.setLevel(okhttp3.logging.HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(context))
            .addInterceptor(logging) // 2. Lo agregamos aquí
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}