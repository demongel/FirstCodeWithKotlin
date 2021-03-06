package com.shakespace.firstlinecode.chapter04broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.chapter04broadcast.practice.BaseActivity
import com.shakespace.firstlinecode.chapter04broadcast.practice.LoginActivity
import com.shakespace.firstlinecode.global.showToast
import com.shakespace.firstlinecode.global.start
import kotlinx.android.synthetic.main.activity_broadcast.*


/**
 * register in manifest : static  静态注册
 * register by programming: dynamic 动态注册
 *
 *
 */
class BroadcastActivity : BaseActivity() {
    //NetworkCapabilities
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    lateinit var networkChangeReceiver: NetworkChangeReceiver

    lateinit var localBroadcastManager: LocalBroadcastManager

    lateinit var localReceiver: LocalReceiver

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broadcast)


        val intentFilter = IntentFilter()
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        networkChangeReceiver = NetworkChangeReceiver()

        registerReceiver(networkChangeReceiver, intentFilter)


        localBroadcastManager = LocalBroadcastManager.getInstance(this)

        val intentFilter2 = IntentFilter()
        intentFilter2.addAction("android.intent.action.MY_CUSTOM_BROADCAST")
        localReceiver = LocalReceiver()
        localBroadcastManager.registerReceiver(localReceiver, intentFilter2)


        initListener()

        connectivityManager.registerNetworkCallback(
            NetworkRequest.Builder().build(),
            NetworkCallbackImpl()
        )
    }

    private fun initListener() {

        //  register in manifest , then find CustomBroadcastReceiver  and run receiver method
        //  this is standard broadcast (no order)
        tv_one.setOnClickListener {
            val intent = Intent("android.intent.action.MY_CUSTOM_BROADCAST")

            // for Android 8.0
            //  two way to solve
            //  1. set component
            //  2. add flags

            //  param 1 : the packagename for receiver app
            // param 2 : the fullname for receiver
//            intent.component = ComponentName(
//                "com.shakespace.firstlinecode",
//                "com.shakespace.firstlinecode.chapter04broadcast.CustomBroadcastReceiver"
//            )

//            intent.component = ComponentName(
//                "com.shakespace.firstlinecode",
//                "com.shakespace.firstlinecode.chapter04broadcast.RemoteReceiver"
//            )

            //  or try addFlags : this will be redline warning, but not error for run
            //  don't set component, just add flags, can send to another app ,even another app use implicit action
            intent.addFlags(0x01000000)
            sendBroadcast(intent) //  no order , every can receive
        }

        tv_two.setOnClickListener {
            val intent = Intent("android.intent.action.MY_CUSTOM_BROADCAST")

            intent.addFlags(0x01000000)
            //  add priority in manifest, then receiver will receive in order
            sendOrderedBroadcast(intent, null)
        }

        //  only can receive in current app
        /**
         *  1. LocalBroadcast only can be registered by dynamic way,no static ,cause LocalBroadcast need a running app to receive
         *  2. benefit :
         *      1.safe, only send and receive in current app
         *      2. higher efficiency
         */
        tv_three.setOnClickListener {

            val intent = Intent("android.intent.action.MY_CUSTOM_BROADCAST")
            localBroadcastManager.sendBroadcast(intent)
        }


        tv_four.setOnClickListener {
            val intent = Intent("com.example.broadcast.FORCE_OFFLINE")
            sendBroadcast(intent)
        }

        tv_five.setOnClickListener {
            start(LoginActivity::class.java)
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(networkChangeReceiver)
        localBroadcastManager.unregisterReceiver(localReceiver)
    }

    @Suppress("DEPRECATION")
    inner class NetworkChangeReceiver : BroadcastReceiver() {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onReceive(context: Context?, intent: Intent?) {
            val networkInfo = connectivityManager.activeNetworkInfo
            // use android.net.ConnectivityManager.NetworkCallback instead
            if (networkInfo != null && networkInfo.isAvailable) {
                context?.showToast(networkInfo.typeName)
            } else {
                context?.showToast("disconect")
            }
        }
    }

    class LocalReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            context?.showToast("This is local broadcast")
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    inner class NetworkCallbackImpl : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            showToast("onAvailable")
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            showToast("onLost")
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities);
            if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    showToast("onCapabilitiesChanged: 网络类型为wifi")
                } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    showToast("onCapabilitiesChanged: 蜂窝网络")
                } else {
                    showToast("onCapabilitiesChanged: 其他网络")
                }
            }
        }
    }

}



