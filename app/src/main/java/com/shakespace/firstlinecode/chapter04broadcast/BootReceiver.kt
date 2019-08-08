package com.shakespace.firstlinecode.chapter04broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.shakespace.firstlinecode.global.TAG
import com.shakespace.firstlinecode.global.showToast

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        //  in most device , need set current app in "run when boot completed" list
        context.showToast("Boot Completed")
        Log.e(TAG, "onReceive: ---------- boot com")
    }
}
