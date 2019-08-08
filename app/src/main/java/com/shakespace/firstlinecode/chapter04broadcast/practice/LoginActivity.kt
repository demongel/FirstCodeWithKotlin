package com.shakespace.firstlinecode.chapter04broadcast.practice

import android.os.Bundle
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.chapter04broadcast.BroadcastActivity
import com.shakespace.firstlinecode.global.showToast
import com.shakespace.firstlinecode.global.start
import kotlinx.android.synthetic.main.activity_loain.*

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loain)


        initLister()

    }

    private fun initLister() {
        tv_login.setOnClickListener {
            val name = et_name.text.toString().trim()
            val password = et_password.text.toString().trim()

            if (name == "admin" && password == "123456") {
                start(BroadcastActivity::class.java)
            }else{
                showToast("account or password is invalid")
            }

        }

    }
}
