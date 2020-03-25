package com.beloushkin.test.catfacts

import com.beloushkin.test.catfacts.catfact.CatFactAction
import com.beloushkin.test.catfacts.catfact.CatFactState
import com.beloushkin.test.catfacts.di.DependencyInjection
import com.ww.roxie.BaseViewModel

class AndroidTestApplication: App() {

    override val di = TestDependencyInjection
}

object TestDependencyInjection: DependencyInjection {
    override val catFactViewModel: BaseViewModel<CatFactAction, CatFactState> = AndroidTestViewModel()
}