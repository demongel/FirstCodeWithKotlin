package com.shakespace.firstlinecode.chapter02ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.global.showToast
import com.shakespace.firstlinecode.global.start
import kotlinx.android.synthetic.main.activity_ui.*

class UIActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ui)

        initView()
    }

    private fun initView() {

        rb_show.setOnClickListener {
            progress_bar.visibility = View.VISIBLE
            rb_show.postDelayed({
                progress_bar.visibility = View.INVISIBLE
            }, 2000)
        }

        rb_hide.setOnClickListener {
            progress_bar.visibility = View.INVISIBLE
        }

        tv_listview.setOnClickListener {
            start(ListViewActivity::class.java)
        }

        tv_recyclerview.setOnClickListener {
            start(RecyclerViewActivity::class.java)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_ui, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.show -> {
                createAlertDialog()
            }
            R.id.share -> showToast("share")
            R.id.quit -> showToast("quit")
        }

        return super.onOptionsItemSelected(item)
    }

    fun createAlertDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("Hint")
            setMessage("Are you OK?")
            setPositiveButton("OK") { _, _ ->
                showToast("you click OK")
            }

            setNegativeButton("Cancel") { _, _ ->
                showToast("Canceled")
            }
            show()
        }

    }

}
