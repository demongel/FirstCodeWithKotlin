package com.shakespace.firstlinecode.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.global.showToast


class RecyclerViewAdapter :
    ListAdapter<String, RecyclerViewAdapter.ViewHolder>(ItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_view_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        getItem(position).let {
            with(holder) {
                tvTitle.text = position.toString()
                tvContent.text = it
                itemView.setOnClickListener {
                    itemView.context.showToast("click $position")
                }
            }
        }
    }


    //  // 2019-07-23 NOTE: not a good way, still some refresh problem
    override fun submitList(list: MutableList<String>?) {
        super.submitList(list?.toList())
    }


    inner class ViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        var tvTitle: TextView
        var tvContent: TextView


        init {
            tvTitle = itemView.findViewById(R.id.tv_title)
            tvContent = itemView.findViewById(R.id.tv_content)

        }

//        fun bind(string: String, listener: View.OnClickListener) {
//            itemView.setOnClickListener {
//                itemView.context.showToast("click string")
//            }
//        }

    }


}

class ItemDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}
