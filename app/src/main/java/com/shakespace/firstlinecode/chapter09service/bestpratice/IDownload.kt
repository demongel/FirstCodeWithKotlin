package com.shakespace.firstlinecode.chapter09service.bestpratice

interface IDownload {

    fun onProgress(progress:Int)

    fun onSuccess()

    fun onFailed()

    fun onPause()

    fun onCancled()

}