package com.beloushkin.test.catfacts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.squareup.moshi.Moshi
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val moshi = Moshi.Builder().build()

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("https://cat-fact.herokuapp.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val catFactService = retrofit.create(CatFactService::class.java)


        getFactButton.setOnClickListener {
            val call = catFactService.getFacts()
            loadingIndicator.isVisible = true
            getFactButton.isEnabled = false
            call.enqueue(object: Callback<Response>{
                override fun onFailure(call: Call<Response>, t: Throwable) {
                    loadingIndicator.isVisible = false
                    getFactButton.isEnabled = true
                    errorView.isVisible = true

                }

                override fun onResponse(
                    call: Call<Response>,
                    response: retrofit2.Response<Response>
                ) {
                    loadingIndicator.isVisible = false
                    getFactButton.isEnabled = true
                    errorView.isVisible = false

                    val randomInt = Random.nextInt(0, response.body()!!.all.size - 1)
                    catFactView.text = response.body()!!.all[randomInt].text
                }
            })
        }
    }
}
