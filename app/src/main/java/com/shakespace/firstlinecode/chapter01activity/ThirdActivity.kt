package com.shakespace.firstlinecode.chapter01activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.global.TAG
import com.shakespace.firstlinecode.global.start
import kotlinx.android.synthetic.main.activity_second.*

//  singleInstance
class ThirdActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_third)

        initListener()
    }

    private fun initListener() {

        tv_one.setOnClickListener {
            start(FirstActivity::class.java)
        }

        tv_two.setOnClickListener {
            start(SecondActivity::class.java)
        }

        tv_three.setOnClickListener {
            start(ThirdActivity::class.java)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.e(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.e(this.TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.e(this.TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.e(this.TAG, "onStop: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(this.TAG, "onDestroy: ")
    }
}
