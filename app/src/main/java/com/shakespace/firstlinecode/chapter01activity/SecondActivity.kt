package com.shakespace.firstlinecode.chapter01activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.shakespace.firstlinecode.R
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val data = intent.getStringExtra("data")

        textView2.text = data
    }


    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra("result", "result")
        setResult(Activity.RESULT_OK, intent)
        super.onBackPressed() //  call finish by yourself or call super after setResult ,or your will not receive the result
    }
}
