package com.shakespace.firstlinecode.chapter04broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.shakespace.firstlinecode.global.TAG
import com.shakespace.firstlinecode.global.showToast

class RemoteReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        Log.e(this.TAG, "onReceive: RemoteReceiver")
        // in emulator will show in order
        // but in some device like redmi , only show the last text
        context.showToast("this is remote receiver")
        context.showToast("this is remote receiver2")
//        context.showToast("this is remote receiver")
        abortBroadcast()
    }
}
