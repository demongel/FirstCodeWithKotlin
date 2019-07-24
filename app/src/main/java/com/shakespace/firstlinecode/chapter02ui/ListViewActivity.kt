package com.shakespace.firstlinecode.chapter02ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.adapter.ListViewAdapter
import kotlinx.android.synthetic.main.activity_list_view.*

class ListViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)


        val data = createData()

        val adapter = ListViewAdapter(this,data)

        list_view.adapter= adapter

    }

    private fun createData(): MutableList<String> {
        val mutableList = mutableListOf<String>()
        for (i in 0..100) {
            mutableList.add(i.toString())
        }

        return mutableList
    }
}
