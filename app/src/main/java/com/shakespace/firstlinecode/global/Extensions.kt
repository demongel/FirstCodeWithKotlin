package com.shakespace.firstlinecode.global

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast

/**
 * easy to use TAG for every Class
 */
val Any.TAG: String
    get() {
        return if (!javaClass.isAnonymousClass) {
            val name = javaClass.simpleName
            if (name.length <= 23) name else name.substring(0, 23)
        } else {
            val name = javaClass.name
            if (name.length <= 23) name else name.substring(0, 23)
        }
    }


/**
 * [clazz] : target activity
 * [bundle] : bundle data
 */
fun Activity.start(clazz: Class<*>, bundle: Bundle? = null) {
    startActivity(Intent(this, clazz).also {
        if (bundle != null) {
            it.putExtra("bundle", bundle)
        }
    })
}


fun Activity.kill() {
    android.os.Process.killProcess(android.os.Process.myPid())
}

fun Context.showToast(msg: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, duration).show()
}


//  need  need androidx  for this.currentList
//fun <T, VH : RecyclerView.ViewHolder> ListAdapter<T, VH>.updateList(list: List<T>?) {
//    this.submitList(if (list == this.currentList) list?.toList() else list)
//}


