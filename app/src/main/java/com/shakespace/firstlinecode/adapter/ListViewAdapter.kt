package com.shakespace.firstlinecode.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.global.showToast

class ListViewAdapter(val context: Context, var mutableList: MutableList<String>) : BaseAdapter() {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertview = convertView
        val holder: ViewHolder
        if (convertview == null) {
            holder = ViewHolder()
            convertview = LayoutInflater.from(context).inflate(R.layout.list_view_item, parent, false)

            holder.tv_title = convertview!!.findViewById(R.id.tv_title)
            holder.tv_content = convertview.findViewById(R.id.tv_content)

            convertview.tag = holder
        } else {
            holder = convertview.tag as ViewHolder
        }

        holder.tv_title?.text = position.toString()
        holder.tv_content?.text = mutableList.get(position)

        convertview.setOnClickListener {
            context.showToast("click ${mutableList.get(position)}")
        }


        return convertview
    }

    override fun getItem(position: Int): String {
        return mutableList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return mutableList.size
    }


    // internal just for current module
    inner class ViewHolder {
        internal var tv_title: TextView? = null
        internal var tv_content: TextView? = null
    }

}


