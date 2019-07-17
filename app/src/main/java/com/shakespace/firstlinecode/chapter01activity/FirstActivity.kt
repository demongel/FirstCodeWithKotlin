package com.shakespace.firstlinecode.chapter01activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.shakespace.firstlinecode.R

class FirstActivity : AppCompatActivity() {

    var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.add -> showToast("add")
            R.id.share -> showToast("share")
            R.id.quit -> showToast("quit")

        }


        return super.onOptionsItemSelected(item)
    }

    fun showToast(msg: String) {
        if (toast == null) {
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
        } else {
            toast?.setText(msg)
        }
        toast?.show()
    }

}
