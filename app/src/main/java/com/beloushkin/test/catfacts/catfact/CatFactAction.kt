package com.beloushkin.test.catfacts.catfact

import com.ww.roxie.BaseAction

sealed class CatFactAction: BaseAction {

    object GetFactButtonClicked: CatFactAction()
}