package com.shakespace.firstlinecode

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shakespace.firstlinecode.chapter01activity.FirstActivity
import com.shakespace.firstlinecode.chapter02ui.UIActivity
import com.shakespace.firstlinecode.chapter03fragment.FragmentActivity
import com.shakespace.firstlinecode.chapter04broadcast.BroadcastActivity
import com.shakespace.firstlinecode.chapter05persistent.PersistentActivity
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
            // origin
            startActivity(Intent(this, FirstActivity::class.java))
        }

        chapter_two.setOnClickListener {
            //  extension
            start(UIActivity::class.java)
        }

        chapter_three.setOnClickListener {
            start(FragmentActivity::class.java)
        }

        chapter_4.setOnClickListener{
            start(BroadcastActivity::class.java)
        }

        chapter_5.setOnClickListener{
            start(PersistentActivity::class.java)
        }

    }


}
