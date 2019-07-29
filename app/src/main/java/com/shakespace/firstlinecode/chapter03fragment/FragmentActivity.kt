package com.shakespace.firstlinecode.chapter03fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.chapter03fragment.fragment.InnerFragment
import com.shakespace.firstlinecode.global.showToast
import com.shakespace.firstlinecode.global.start
import kotlinx.android.synthetic.main.bottom_fragment.*
import kotlinx.android.synthetic.main.top_fragment.*

/**
 *  can change layout depends on orientation
 */
class FragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        initListener()
    }


    private fun initListener() {
        //  can get view which in fragment  directly
        tv_left.setOnClickListener {

            val fragment = supportFragmentManager.findFragmentByTag("bottom")

            if (fragment != null) {
                showToast("has added")
            } else {
                supportFragmentManager  //  get manager
                    .beginTransaction() // begin
                    .replace(R.id.bottom_container, InnerFragment(), "bottom") // replace
                    .addToBackStack("fragment")
                    .commit() // commit
            }

        }

        tv_right.setOnClickListener {
            val fragment = supportFragmentManager.findFragmentByTag("top")

            if (fragment != null) {
                showToast("has added")
            } else {
                supportFragmentManager  //  get manager
                    .beginTransaction() // begin
                    .replace(R.id.top_container, InnerFragment(), "top") // replace
                    .addToBackStack("fragment")
                    .commit() // commit
            }
        }


        tv_news.setOnClickListener {
            start(NewsActivity::class.java)
        }

    }
}
