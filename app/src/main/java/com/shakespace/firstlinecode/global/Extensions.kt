package com.shakespace.firstlinecode.global

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

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
fun Context.start(clazz: Class<*>, bundle: Bundle? = null) {
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

val Context.orientation: Int
    get() {
        return this.resources.configuration.orientation
    }

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
    val fragmentTransaction = beginTransaction()
    fragmentTransaction.func()
    fragmentTransaction.commit()
}


fun View.dp2px(dp: Float): Int {
    val px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics)
    return px.toInt()
}

fun View.dpToPx(dp: Float): Int {
    val scale = context.resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

//  need androidx  for this.currentList
//  current version :androidx.recyclerview:recyclerview:1.1.0-beta01
fun <T, VH : RecyclerView.ViewHolder> ListAdapter<T, VH>.updateList(list: List<T>?) {
    this.submitList(if (list == this.currentList) list.toList() else list)
}


