package com.shakespace.firstlinecode.chapter09service.bestpratice

import android.os.AsyncTask
import android.os.Environment
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.InputStream
import java.io.RandomAccessFile

/**
 * String : for url
 * Int : for progress
 * Int :  for result
 */
const val TYPE_SUCCESS = 0
const val TYPE_FAILED = 1
const val TYPE_PAUSED = 2
const val TYPE_CANCLED = 3

class DownloadTask(val iDownload: IDownload) : AsyncTask<String, Int, Int>() {



    override fun doInBackground(vararg params: String?): Int {

        val inputStream : InputStream
        val accessFile : RandomAccessFile

        try {
            var downloadedSize : Long = 0
            val downloadUrl = params[0]

            val fileName = downloadUrl?.substringAfterLast("/")

            val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path

            val file = File(dir+fileName)

            if(file.exists()){
                downloadedSize = file.length()
            }

//            var contentSize =

        }catch (e:Exception){

        }

        return 0
    }


    fun getContentSize (url : String) : Long{
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()

        val response = client.newCall(request).execute()
        if(response.isSuccessful){
            val contentLength = response.body()?.contentLength()
            response.close()
            return contentLength ?: 0
        }
        return 0
    }

}