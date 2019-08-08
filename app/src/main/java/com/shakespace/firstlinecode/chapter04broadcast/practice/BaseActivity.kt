package com.shakespace.firstlinecode.chapter04broadcast.practice

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.global.start

open class BaseActivity : AppCompatActivity() {

    lateinit var offlineReceiver: OfflineReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loain)

        ActivityController.add(this)
    }

    override fun onDestroy() {
        super.onDestroy()

        ActivityController.remove(this)
    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.example.broadcast.FORCE_OFFLINE")

        offlineReceiver= OfflineReceiver()
        registerReceiver(offlineReceiver,intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(offlineReceiver)
    }


    class OfflineReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            context?.let {
                AlertDialog.Builder(it).apply {
                    setTitle("Hint")
                    setMessage("You are forced to be offline")
                    setCancelable(false)
                    //  finish ,and login again.
                    setPositiveButton("OK") { _, _ ->
                        ActivityController.clearAll()
                        context.start(LoginActivity::class.java)
                    }
                    show()
                }
            }

        }

    }
}
