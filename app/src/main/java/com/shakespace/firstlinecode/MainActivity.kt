package com.shakespace.firstlinecode

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.shakespace.firstlinecode.chapter01activity.FirstActivity
import com.shakespace.firstlinecode.chapter02ui.UIActivity
import com.shakespace.firstlinecode.global.start
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initListener()

    }

    private fun initListener() {

        chapter_one.setOnClickListener {
            startActivity(Intent(this, FirstActivity::class.java))
        }

        chapter_two.setOnClickListener{
            start(UIActivity::class.java)
        }

    }


}
