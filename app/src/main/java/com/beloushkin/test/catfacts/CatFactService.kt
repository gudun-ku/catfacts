package com.beloushkin.test.catfacts

import retrofit2.Call
import retrofit2.http.GET

interface CatFactService {

    @GET("facts")
    fun getFacts(): Single<Response>
}