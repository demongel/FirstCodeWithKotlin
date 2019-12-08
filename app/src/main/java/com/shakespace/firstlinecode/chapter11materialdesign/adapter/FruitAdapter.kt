package com.shakespace.firstlinecode.chapter11materialdesign.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.chapter11materialdesign.bean.Fruit
import com.shakespace.firstlinecode.chapter11materialdesign.components.CollapsingToolbarActivity
import com.shakespace.firstlinecode.chapter11materialdesign.components.FRUIT_IMAGE_ID
import com.shakespace.firstlinecode.chapter11materialdesign.components.FRUIT_NAME

/**
 * created by  shakespace
 * 2019/12/5  23:47
 */

class FruitAdapter(val list: List<Fruit>) :
    RecyclerView.Adapter<FruitAdapter.ViewHolder>() {

    val size: Int
        get() = if (list.isEmpty()) 0 else list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.fruit_item, parent, false)
        val holder = ViewHolder(view)

        holder.cardView.setOnClickListener {
            val position = holder.adapterPosition
            val intent = Intent(parent.context, CollapsingToolbarActivity::class.java).also {
                val fruit = list.get(position)
                it.putExtra(FRUIT_NAME, fruit.name)
                it.putExtra(FRUIT_IMAGE_ID, fruit.imageID)
            }
            parent.context.startActivity(intent)
        }

        return holder
    }

    override fun getItemCount(): Int {
        return size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit = list[position]
        holder.fruitName.setText(fruit.name)
        Glide.with(holder.cardView).load(fruit.imageID).into(holder.fruitImage)


    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var fruitImage: ImageView = itemView.findViewById(R.id.fruit_image)
        var fruitName: TextView = itemView.findViewById(R.id.fruit_name)
        var cardView: CardView = itemView as CardView
    }
}