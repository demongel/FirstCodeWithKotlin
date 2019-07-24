package com.shakespace.firstlinecode.chapter02ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.adapter.RecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_recycler_view.*

class RecyclerViewActivity : AppCompatActivity() {

    lateinit var data: MutableList<String>

    lateinit var adapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)


        data = createData()

        adapter = RecyclerViewAdapter()

        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = adapter
        recycler_view.addItemDecoration(
            androidx.recyclerview.widget.DividerItemDecoration(
                this,
                androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
            )
        )

        adapter.submitList(data)


    }

    private fun createData(): MutableList<String> {
        val mutableList = mutableListOf<String>()
        for (i in 0..100) {
            mutableList.add(i.toString())
        }

        return mutableList
    }

    private fun createNewData(): MutableList<String> {
        val mutableList = mutableListOf<String>()
//        for (i in 50 downTo 0) {
        for (i in 3..100) {
            mutableList.add(i.toString())
        }

        return mutableList
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_ui_recycler_view, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        //  not compare index of list , so index will not changed if string is the same,
        when (item?.itemId) {
            R.id.remove -> {
                data = createNewData()
                adapter.submitList(data)
//                adapter.notifyDataSetChanged()
            }
            R.id.update -> {
                data[0] = "update 0"
                data[1] = "update 1"
                data[2] = "updata 2"
                //  submitList  couldnot refresh ？？
//                adapter.submitList(mutableListOf<String>().also { it.addAll(data) })
                adapter.submitList(data)
//                adapter.notifyDataSetChanged()
            }
            R.id.add -> {

                data = createData()
                adapter.submitList(data)
//                adapter.notifyDataSetChanged()
            }

        }

        return super.onOptionsItemSelected(item)
    }
}
