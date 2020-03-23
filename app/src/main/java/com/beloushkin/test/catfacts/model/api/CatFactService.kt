package com.beloushkin.test.catfacts.model.api

import com.beloushkin.test.catfacts.model.Response
import io.reactivex.Single
import retrofit2.http.GET

interface CatFactService {

    @GET("facts")
    fun getFacts(): Single<Response>
}