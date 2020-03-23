package com.beloushkin.test.catfacts.catfact

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.beloushkin.test.catfacts.R
import com.beloushkin.test.catfacts.di
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class CatFactActivity : AppCompatActivity() {

    private lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val catFactRepository = di.catFactRepository
        getFactButton.setOnClickListener {
            disposable =  catFactRepository.getFact()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    loadingIndicator.isVisible = true
                }
                .doOnError {
                    loadingIndicator.isVisible = false
                    errorView.isVisible = true
                }
                .toObservable()
                .onErrorResumeNext(Observable.empty())
                .subscribe { fact ->
                    loadingIndicator.isVisible = false
                    catFactView.text = fact
                    errorView.isVisible = false
                }

        }
    }

    override fun onDestroy() {
        if(::disposable.isInitialized) {
            disposable.dispose()
        }
        super.onDestroy()
    }
}
