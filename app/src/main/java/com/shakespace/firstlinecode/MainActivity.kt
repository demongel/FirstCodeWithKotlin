package com.shakespace.firstlinecode

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.shakespace.firstlinecode.chapter01activity.FirstActivity
import com.shakespace.firstlinecode.chapter02ui.UIActivity
import com.shakespace.firstlinecode.chapter03fragment.FragmentActivity
import com.shakespace.firstlinecode.chapter04broadcast.BroadcastActivity
import com.shakespace.firstlinecode.chapter05persistent.PersistentActivity
import com.shakespace.firstlinecode.chapter06contentprovider.ContentProviderActivity
import com.shakespace.firstlinecode.chapter07multimedia.MultiMediaActivity
import com.shakespace.firstlinecode.chapter08network.NetWorkActivity
import com.shakespace.firstlinecode.chapter09service.ServiceActivity
import com.shakespace.firstlinecode.chapter10LBS.LBSActivity
import com.shakespace.firstlinecode.chapter11materialdesign.MaterialDesignActivity
import com.shakespace.firstlinecode.chapter13weather.ui.WeatherActivity
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

        chapter_4.setOnClickListener {
            start(BroadcastActivity::class.java)
        }

        chapter_5.setOnClickListener {
            start(PersistentActivity::class.java)
        }

        chapter_6.setOnClickListener {
            start(ContentProviderActivity::class.java)
        }

        chapter_7.setOnClickListener {
            start(MultiMediaActivity::class.java)
        }

        chapter_8.onClick(NetWorkActivity::class.java)

        chapter_9.onClick(ServiceActivity::class.java)

        chapter_10.onClick(LBSActivity::class.java)

        chapter_11.onClick(MaterialDesignActivity::class.java)

        chapter_13.onClick(WeatherActivity::class.java)

    }

    private fun View.onClick(clazz: Class<*>) {
        setOnClickListener {
            start(clazz)
        }
    }


}
