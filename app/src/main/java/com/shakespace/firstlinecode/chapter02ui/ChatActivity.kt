package com.shakespace.firstlinecode.chapter02ui

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.adapter.ChatAdapter
import com.shakespace.firstlinecode.chapter02ui.bean.ChatMsg
import com.shakespace.firstlinecode.chapter02ui.bean.TYPE_RECEIVE
import com.shakespace.firstlinecode.chapter02ui.bean.TYPE_SEND
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {

    val chatList = mutableListOf<ChatMsg>()
    var type = TYPE_SEND
    lateinit var adapter: ChatAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)


        initListener()

        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        adapter = ChatAdapter()
        recycler_view.layoutManager = layoutManager
        recycler_view.adapter = adapter

    }

    private fun initListener() {

        msg.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                send.isEnabled = !TextUtils.isEmpty(s)
            }

        })


        send.setOnClickListener {
            val chatMsg = ChatMsg(msg.text.toString(), type)
            chatList.add(chatMsg)
            adapter.submitList(chatList)//  no  refresh
//            adapter.updateList(chatList)
//            adapter.notifyDataSetChanged()
            recycler_view.scrollToPosition(chatList.size - 1) //  refresh
        }


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_ui_chat, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.send -> {
                type = TYPE_SEND
            }
            R.id.receive -> {
                type = TYPE_RECEIVE
            }

        }

        return super.onOptionsItemSelected(item)
    }
}
