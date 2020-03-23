package com.beloushkin.test.catfacts

import android.app.Application
import android.content.Context
import com.beloushkin.test.catfacts.di.DependencyInjection
import com.beloushkin.test.catfacts.di.DependencyInjectionImpl

class App: Application() {

    val di: DependencyInjection by lazy {
        DependencyInjectionImpl()
    }
}

val Context.di: DependencyInjection
    get() = (this.applicationContext as App).di

