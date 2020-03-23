package com.beloushkin.test.catfacts.di

import com.beloushkin.test.catfacts.CatFactService
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface DependencyInjection {
}

class DependencyInjectionImpl: DependencyInjection {
    init{
        val moshi = Moshi.Builder().build()

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("https://cat-fact.herokuapp.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val catFactService = retrofit.create(CatFactService::class.java)
    }
}