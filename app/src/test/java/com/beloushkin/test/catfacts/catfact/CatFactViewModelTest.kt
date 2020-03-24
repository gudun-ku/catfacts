package com.beloushkin.test.catfacts.catfact

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.beloushkin.test.catfacts.RxTextSchedulerRule
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.RuntimeException

class CatFactViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testRule: RxTextSchedulerRule = RxTextSchedulerRule()

    val catFactUseCase = mock<CatFactUseCase>()

    val observer = mock<Observer<CatFactState>>()

    lateinit var testSubject: CatFactViewModel

    @Before
    fun setup() {
        testSubject = CatFactViewModel(catFactUseCase)
        testSubject.observableState.observeForever(observer)
    }

    @Test
    fun `Given fact succesfully loaded when action GetFact is received, then State contains fact`() {
        //GIVEN
        val fact = "Programmers love cats"
        val succesfulState = CatFactState(loading = false, catFact = fact)
        val loadingState = CatFactState(loading = true)
        val errorState = CatFactState(loading = false, displayError = true)

        whenever(catFactUseCase.getFact()).thenReturn(Single.just(fact))
        //WHEN
        testSubject.dispatch(CatFactAction.GetFactButtonClicked)
        testRule.triggerActions()

        //THEN
        inOrder(observer) {
            verify(observer).onChanged(CatFactState()) // Initial state
            verify(observer).onChanged(loadingState) // loading
            verify(observer).onChanged(succesfulState)
        }

        verifyNoMoreInteractions(observer)
    }

    // Error case
    @Test
    fun `Given fact failed to load when action GetFact is received, then State contains error`() {
        //GIVEN
        val fact = "Programmers love cats"
        val loadingState = CatFactState(loading = true)
        val errorState = CatFactState(loading = false, displayError = true)

        whenever(catFactUseCase.getFact()).thenReturn(Single.error(RuntimeException()))
        //WHEN
        testSubject.dispatch(CatFactAction.GetFactButtonClicked)
        testRule.triggerActions()

        //THEN
        inOrder(observer) {
            verify(observer).onChanged(CatFactState()) // Initial state
            verify(observer).onChanged(loadingState) // loading
            verify(observer).onChanged(errorState)
        }

        verifyNoMoreInteractions(observer)
    }
}