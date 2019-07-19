package com.shakespace.firstlinecode.global

import android.util.Log


inline fun <reified T> T.logd(msg: String) = Log.d(T::class.java.simpleName, msg)

inline fun <reified T> T.logi(msg: String) = Log.i(T::class.java.simpleName, msg)

inline fun <reified T> T.logw(msg: String) = Log.w(T::class.java.simpleName, msg)

inline fun <reified T> T.loge(msg: String) = Log.e(T::class.java.simpleName, msg)

