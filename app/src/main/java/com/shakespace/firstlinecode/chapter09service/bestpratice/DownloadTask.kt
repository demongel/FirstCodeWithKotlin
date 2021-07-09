package com.shakespace.firstlinecode.chapter09service.bestpratice

import android.os.AsyncTask
import android.os.Environment
import android.util.Log
import com.shakespace.firstlinecode.global.TAG
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
const val TYPE_CANCELED = 3
const val TYPE_EXISTED = 4

@Suppress("DEPRECATION")
class DownloadTask(val iDownload: IDownload) : AsyncTask<String, Int, Int>() {

    private var isCanceled = false
    private var isPaused = false

    private var lastProgress = 0

    override fun doInBackground(vararg params: String?): Int {

        var inputStream: InputStream? = null
        var accessFile: RandomAccessFile? = null
        var file: File? = null
        try {
            var downloadedSize: Long = 0
            val downloadUrl = params[0]
            val fileName = downloadUrl?.substringAfterLast("/")
            val dir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path
            file = File(dir + fileName)
            if (file.exists()) {
                downloadedSize = file.length()
            }

            val contentSize = downloadUrl?.let { getContentSize(it) } ?: 0

            if (contentSize == 0L) {
                return TYPE_FAILED
            } else if (contentSize == downloadedSize) {
                return TYPE_EXISTED
            }

            val client = OkHttpClient()
            val request = Request.Builder()
                .addHeader("RANGE", "bytes=$downloadedSize-")
                .url(downloadUrl!!)
                .build()

            val response = client.newCall(request).execute()
            inputStream = response.body()?.byteStream()
            accessFile = RandomAccessFile(file, "rw")
            accessFile.seek(downloadedSize)

            inputStream?.let {
                val bytes = ByteArray(1024)
                var total = 0
                var len: Int
                while ((inputStream.read(bytes).also {
                        len = it
                    }) != -1) {
                    when {
                        isCanceled -> return TYPE_CANCELED
                        isPaused -> return TYPE_PAUSED
                        else -> {
                            total += len
                            accessFile.write(bytes, 0, len)
                            val progress = ((total + downloadedSize) * 100 / contentSize).toInt()
                            publishProgress(progress)
                        }
                    }
                }
            }

            response.body()?.close()
            return TYPE_SUCCESS

        } catch (e: Exception) {
            Log.e(this.TAG, "doInBackground: ")

        } finally {
            try {
                inputStream?.close()
                accessFile?.close()
                if (isCanceled && file != null) {
                    file.delete()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return TYPE_FAILED
    }

    override fun onProgressUpdate(vararg values: Int?) {
        var process = values[0]
        process?.let {
            if (process > lastProgress) {
                iDownload.onProgress(process)
                lastProgress = process
            }
        }
    }

    fun pauseDownload() {
        isPaused = true
    }

    fun cancelDownload() {
        isCanceled = true
    }

    override fun onPostExecute(result: Int?) {
        when (result) {
            TYPE_FAILED -> iDownload.onFailed()
            TYPE_CANCELED -> iDownload.onCancled()
            TYPE_SUCCESS -> iDownload.onSuccess()
            TYPE_PAUSED -> iDownload.onPause()
            TYPE_EXISTED -> iDownload.onExisted()
            else -> ""
        }
    }

    fun getContentSize(url: String): Long {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()

        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            val contentLength = response.body()?.contentLength()
            response.close()
            return contentLength ?: 0
        }
        return 0
    }

}