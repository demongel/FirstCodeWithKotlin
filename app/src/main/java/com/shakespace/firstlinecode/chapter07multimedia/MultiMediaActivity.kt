package com.shakespace.firstlinecode.chapter07multimedia

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.global.TAG
import com.shakespace.firstlinecode.global.showToast
import com.shakespace.firstlinecode.global.start
import kotlinx.android.synthetic.main.activity_multi_media.*
import java.io.File

@Suppress("DEPRECATION")
class MultiMediaActivity : AppCompatActivity() {

    private var imageUri: Uri? = null

    private val TAKE_PHOTO = 1
    private val PICK_PHOTO = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multi_media)


        initListener()
    }

    private fun initListener() {

        tv_notification.setOnClickListener {

            /**
             *  1. must create NotificationChannel if version >8.0
             *  2. PendingIntent , can hold an intent , even current context has destroyed,still can transition to other context
             *
             *      PendingIntent.getActivity -> to Activity
             *      PendingIntent.getBroadcast() -> to Broadcast  Context.sendBroadcast()
             *      PendingIntent.getService()   -> to Service   Context.startService()
             *
             *  3. params3 is requestCode
             *  4. flags : if you have two pendingIntent
             *      one shot: only one time , so only the first you click will working
             *      FLAG_CANCEL_CURRENT : when you create pendingIntent2, pendingIntent1 will invalid
             *      FLAG_NO_CREATE  :  both not work
             *      FLAG_UPDATE_CURRENT : p2 will cover p1
             *
             *      also can pass 0 .
             */
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    "first_code",
                    "first_code",
                    NotificationManager.IMPORTANCE_LOW
                )
                channel.enableLights(true)
                channel.lightColor = R.color.green
                //  will show number on you app icon
                channel.setShowBadge(true)
                channel.lockscreenVisibility = 1000
                //  if you have set IMPORTANCE_HIGH , this will not working
                // if you have set pendingIntent+ IMPORTANT_DEFAULT, not working
                channel.enableVibration(false)
                channel.shouldVibrate()

                notificationManager.createNotificationChannel(channel)
            }

            val pendingIntent =
                PendingIntent.getActivity(this, 0, Intent(this, GambleActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }, 0)

            val notification = NotificationCompat.Builder(this, "first_code")
                //  xml(vector) is not acceptable here , only png or jpg
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.notification))
                .setSmallIcon(android.R.drawable.stat_notify_chat)  //  saw in status bar
                .setContentTitle("A new message")
                .setContentText("welcome to Macao gamble system")
                .setWhen(System.currentTimeMillis())

                .setLights(Color.GREEN, 1000, 1000)

//                .setVibrate(longArrayOf(100,200,300,400))

//                .setSound(Uri.fromFile(File("/system/media/audio/ringtones/Luna.ogg")))

                // set custom view
//                .setContent()
                // if not setIntent ,autoCancel not work
                .setAutoCancel(true)
                // when click will trigger
                .setContentIntent(pendingIntent)
                // also can be trigger when  user clear notification
//                .setDeleteIntent(pendingIntent)
                // in some cases , will trigger intent directly ,such as when you in a call or in a game
                //  may need add permission SYSTEM_ALERT_WiNDOW
//                .setFullScreenIntent(pendingIntent,true)
                .build()

            notificationManager.notify(1, notification)

//            notificationManager.cancelAll()
            // cancel particular notification,pass an ID , as you passed in notify method
//            notificationManager.cancel(1)
        }


        tv_notification2.setOnClickListener {
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    "first_code",
                    "first_code",
                    NotificationManager.IMPORTANCE_HIGH
                )

                notificationManager.createNotificationChannel(channel)
            }

            val pendingIntent =
                PendingIntent.getActivity(this, 0, Intent(this, GambleActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }, 0)

            val notification = NotificationCompat.Builder(this, "first_code")
                //  xml(vector) is not acceptable here , only png or jpg
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.notification))
                .setSmallIcon(android.R.drawable.stat_notify_chat)  //  saw in status bar
                .setContentTitle("A new message")
                // could not show a very long text , use setStyle instead
//                .setContentText("welcome to Macao gamble system , you can do anything you want here , and buy anything if you have enough money")
                .setStyle(
                    NotificationCompat.BigTextStyle().bigText(
                        "welcome to Macao gamble system , you can do anything you want here , and buy anything if you have enough money"
                    ).setSummaryText("Welcome to Macao gamble")
                )
                // set picture , but only long press can expand
                .setStyle(
                    NotificationCompat.BigPictureStyle().bigPicture(
                        BitmapFactory.decodeResource(resources, R.drawable.banner)
                    )
                )
                .setWhen(System.currentTimeMillis())


                // set custom view
//                .setContent()
                // if not setIntent ,autoCancel not work
                .setAutoCancel(true)
                // when click will trigger
                .setContentIntent(pendingIntent)
                // also can be trigger when  user clear notification
//                .setDeleteIntent(pendingIntent)
                // in some cases , will trigger intent directly ,such as when you in a call
//                .setFullScreenIntent(pendingIntent,true)
                .build()

            // show different msg if id is not same
            notificationManager.notify(2, notification)

//            notificationManager.cancelAll()
            // cancel particular notification,pass an ID , as you passed in notify method
//            notificationManager.cancel(1)
        }


        tv_take_photo.setOnClickListener {
            // sdcard/Android/data/<package name> /cache  , do not need runtime permission
            val file = File(externalCacheDir, "my_photo.jpg")
            if (file.exists()) {
                file.delete()
            }
            file.createNewFile()

            if (Build.VERSION.SDK_INT > 24) {
                /**
                 *  1. context
                 *  2. a unique string , define in manifest
                 *  3. file
                 *
                 *  FileProvider is a ContentProvider
                 *
                 *
                <provider
                android:name="androidx.core.content.FileProvider"
                android:authorities="com.shakepspace.firstcode.provider"  // usually <package name >.provider
                android:exported="false"        // must be false
                android:grantUriPermissions="true">
                <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"  // an immutable  value
                android:resource="@xml/file_path" />
                </provider>
                 *
                 *
                 */
                imageUri =
                    FileProvider.getUriForFile(this, "com.shakepspace.firstcode.provider", file)
            } else {
                imageUri = Uri.fromFile(file)
            }

            // change to 使用ActivityResultContract
            startActivityForResult(Intent("android.media.action.IMAGE_CAPTURE").apply {
                putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
            }, TAKE_PHOTO)
        }

        tv_photo_pick.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    1
                )
            } else {
                pickPhoto()
            }
        }

        tv_audio.setOnClickListener {
            start(AudioPlayActivity::class.java)
        }

        tv_video.setOnClickListener {
            start(VideoPlayActivity::class.java)
        }

    }

    private fun pickPhoto() {
        //  method 1  (this is better to use system gallery)
        // uri like : content://com.miui.gallery.open/raw/%2Fstorage%2Femulated%2F0%2FDCIM%2FCamera%2FIMG_20190528_211034.jpg
        startActivityForResult(
            Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            ), PICK_PHOTO
        )

        // method 2
        // uri like: content://com.android.providers.media.documents/document/image%3A5056
//        startActivityForResult(Intent(Intent.ACTION_GET_CONTENT).also { it.type = "image/*" },PICK_PHOTO)

        // in addition , use com.android.camera.action.CROP to crop photo

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickPhoto()
                } else {
                    showToast("do not have permission")
                }
            }
            else -> Log.e(TAG, "onRequestPermissionsResult: " )
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                TAKE_PHOTO -> {
                    val bitmap =
                        BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri!!))
                    iv_photo.setImageBitmap(bitmap)
                }

                PICK_PHOTO -> {
                    val uri = data?.data
                    uri?.apply {
                        iv_photo.setImageURI(uri)
                        iv_photo.rotation = 90.0f
                    }
                }

                else -> ""
            }

        }

    }
}
