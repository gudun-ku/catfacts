package com.beloushkin.test.catfacts

import android.app.Application
import android.content.Context
import com.beloushkin.test.catfacts.di.DependencyInjection
import com.beloushkin.test.catfacts.di.DependencyInjectionImpl

class App: Application() {

    val di: DependencyInjection by lazy {
        DependencyInjectionImpl(getString(R.string.api_url))
    }
}

val Context.di: DependencyInjection
    get() = (this.applicationContext as App).di

