package com.shakespace.firstlinecode.chapter08network

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.reflect.TypeToken
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.chapter08network.network.Api
import com.shakespace.firstlinecode.chapter08network.network.GithubApi
import com.shakespace.firstlinecode.global.TAG
import com.shakespace.firstlinecode.global.start
import kotlinx.android.synthetic.main.activity_net_work.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NetWorkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net_work)




        tv_to_webview.setOnClickListener {
            start(WebViewActivity::class.java)
        }


        tv_github.setOnClickListener {
            val api = GithubApi.createApi(Api::class.java, "https://api.github.com/")

            GlobalScope.launch(Dispatchers.Main) {
                try {
                    result.text = "Coroutine start!"
                    Log.e(this.TAG, "onCreate:startTime --  ${System.currentTimeMillis()}")
                    val contributors = api.getContributors("square", "retrofit").await()
                    Log.e(this.TAG, "onCreate:endTime --  ${System.currentTimeMillis()}")
                    Log.e(this.TAG, "onCreate: $contributors")

                    result.text = "TOP1 is " + contributors[0].login
                } catch (e: Exception) {
                    Log.e(this.TAG, "onCreate: ${e.localizedMessage}")
                }

            }
        }


        //  get Type in Kotlin
        object : TypeToken<List<String>>() {
        }.type
    }
}
