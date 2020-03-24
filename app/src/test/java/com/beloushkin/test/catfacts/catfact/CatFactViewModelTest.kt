package com.beloushkin.test.catfacts.catfact

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.beloushkin.test.catfacts.RxTextSchedulerRule
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Observer
import org.junit.Rule

class CatFactViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testRule: RxTextSchedulerRule = RxTextSchedulerRule()

    val catFactUseCase = mock<CatFactUseCase>()

    val observer = mock<Observer<CatFactState>>()

    val testSubject = CatFactViewModel(catFactUseCase)
}