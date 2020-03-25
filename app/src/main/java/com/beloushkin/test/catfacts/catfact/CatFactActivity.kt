package com.beloushkin.test.catfacts.catfact

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.beloushkin.test.catfacts.R
import com.beloushkin.test.catfacts.di
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*

class CatFactActivity : AppCompatActivity() {

    private lateinit var disposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val catFactViewModel = di.catFactViewModel
        catFactViewModel.observableState.observe(this, Observer { state ->
            renderState(state)
        })
        getFactButton.setOnClickListener {
            catFactViewModel.dispatch(CatFactAction.GetFactButtonClicked)

        }
    }

    private fun renderState(state: CatFactState) {
        with(state){
            if(catFact.isNotEmpty()) {
                catFactView.text = catFact
            }
            loadingIndicator.isVisible = loading
            getFactButton.isEnabled = true
            errorView.isVisible = displayError
        }
    }

    override fun onDestroy() {
        if(::disposable.isInitialized) {
            disposable.dispose()
        }
        super.onDestroy()
    }
}
