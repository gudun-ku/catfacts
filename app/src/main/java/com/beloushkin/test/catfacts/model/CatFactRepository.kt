package com.beloushkin.test.catfacts.model

import com.beloushkin.test.catfacts.model.api.CatFactService
import io.reactivex.Single
import java.lang.Exception
import kotlin.random.Random

class CatFactRepository(val catFactService: CatFactService) {

    @Throws(Exception::class)
    fun getFact(): Single<String>{
        return catFactService.getFacts()
            .map { response: Response ->
                val randomInt = if(response.all.size > 1) Random.nextInt(0, response.all.size - 1) else 0
                response.all[randomInt].text
            }
    }
}