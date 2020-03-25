package com.beloushkin.test.catfacts

import com.beloushkin.test.catfacts.catfact.CatFactAction
import com.beloushkin.test.catfacts.catfact.CatFactState
import com.ww.roxie.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.TestObserver
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subjects.PublishSubject

class AndroidTestViewModel: BaseViewModel<CatFactAction, CatFactState>() {

    override val initialState: CatFactState = CatFactState()
    val testAction = TestObserver<CatFactAction>()
    val testState = PublishSubject.create<CatFactState>()

    init {
        actions.subscribe(testAction)
        disposables += testState
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(state::setValue)

    }
}