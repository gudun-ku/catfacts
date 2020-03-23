package com.beloushkin.test.catfacts.catfact

import com.beloushkin.test.catfacts.model.CatFactRepository
import io.reactivex.Single

interface CatFactUseCase {
    fun getFact(): Single<String>
}

class CatFactUseCaseImplementation(val catFactRepository: CatFactRepository): CatFactUseCase {
    override fun getFact(): Single<String> {
        return catFactRepository.getFact()
    }
}