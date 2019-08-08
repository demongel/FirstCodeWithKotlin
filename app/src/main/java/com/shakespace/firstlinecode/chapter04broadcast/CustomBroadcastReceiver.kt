package com.shakespace.firstlinecode.chapter04broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.shakespace.firstlinecode.global.TAG
import com.shakespace.firstlinecode.global.showToast

class CustomBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        Log.e(this.TAG, "onReceive: CustomBroadcastReceiver")
        context.showToast("receive custom broadcast------------")

        abortBroadcast()
        Log.e(this.TAG, "onReceive: abort")
    }
}


/**
<receiver
android:name=".chapter04broadcast.CustomBroadcastReceiver"
android:enabled="true"
android:exported="true">

    <intent-filter android:priority="900">
        <action android:name="android.intent.action.MY_CUSTOM_BROADCAST" />
    </intent-filter>

</receiver>

priority: -1000 ~ 10000 , much bigger much higher

 enabled: true is active
exported: true mean can receive broadcast by other app

 */
