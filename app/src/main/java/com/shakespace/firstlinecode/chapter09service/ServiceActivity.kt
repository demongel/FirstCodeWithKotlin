package com.shakespace.firstlinecode.chapter09service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.chapter09service.bestpratice.DownloadActivity
import com.shakespace.firstlinecode.global.TAG
import com.shakespace.firstlinecode.global.start
import kotlinx.android.synthetic.main.activity_service.*

class ServiceActivity : AppCompatActivity() {


    //  create an  anonymous class , use object :AbstractClass
    val connection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            //  only run in some specific flag
            Log.e(this.TAG, "onServiceDisconnected: ")
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {

            Log.e(this.TAG, "onServiceConnected: ")

            val binder = service as CommonService.DownloadBinder

            binder.getProgress()
            binder.startDownload()
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service)


        //  find service in settings-->Developer options-->Running services
        val intent = Intent(this, CommonService::class.java)

        tv_start_service.setOnClickListener {
            startService(intent)
        }

        tv_stop_service.setOnClickListener {
            stopService(intent)
        }


        /*
        E/CommonService: onCreate:
        E/CommonService: onBind:
        E/com.shakespace.firstlin: onServiceConnected:
        E/DownloadBinder: startDownload:
         */
        tv_bind.setOnClickListener {
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }


        /**
         * if not bind , java.lang.IllegalArgumentException: Service not registered
         *
         * if start and bind , or bind and start
         * need both stop and unbind to finish service
         */
        tv_unbind.setOnClickListener {
            unbindService(connection)
        }


        tv_intent_service.setOnClickListener {
            MyIntentService.startActionFoo(this, "", "")
        }

        tv_intent_download.setOnClickListener {
            start(DownloadActivity::class.java)
        }
    }
}

/*




 */
