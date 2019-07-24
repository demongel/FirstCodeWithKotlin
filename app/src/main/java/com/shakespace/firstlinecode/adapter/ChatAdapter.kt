package com.shakespace.firstlinecode.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.chapter02ui.bean.ChatMsg
import com.shakespace.firstlinecode.chapter02ui.bean.TYPE_RECEIVE
import com.shakespace.firstlinecode.chapter02ui.bean.TYPE_SEND


class ChatAdapter :
        ListAdapter<ChatMsg, ChatAdapter.ViewHolder>(MsgDiffCallback()) {


    override fun getItemViewType(position: Int): Int {
        return currentList[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.chat_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val chatMsg = currentList[position]

        if (chatMsg.type == TYPE_SEND) {
            holder.tvSend.text = chatMsg.msg
            holder.receive.visibility = View.GONE
        } else if (chatMsg.type == TYPE_RECEIVE) {
            holder.tvReceive.text = chatMsg.msg
            holder.send.visibility = View.GONE
        }
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var send: LinearLayout = itemView.findViewById(R.id.ll_send)
        var receive: LinearLayout = itemView.findViewById(R.id.ll_receive)
        var tvSend: TextView = itemView.findViewById(R.id.tv_send)
        var tvReceive: TextView = itemView.findViewById(R.id.tv_receive)
    }

}

// efficiency?
class MsgDiffCallback : DiffUtil.ItemCallback<ChatMsg>() {
    override fun areItemsTheSame(oldItem: ChatMsg, newItem: ChatMsg): Boolean {
        return false
    }

    override fun areContentsTheSame(oldItem: ChatMsg, newItem: ChatMsg): Boolean {
        return oldItem.type == newItem.type && oldItem.msg == newItem.msg
//        return false
    }

}



