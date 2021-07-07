package com.shakespace.firstlinecode.chapter11materialdesign.components

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.shakespace.firstlinecode.R
import kotlinx.android.synthetic.main.activity_collapsing_toolbar.*

val FRUIT_NAME = "fruit_name"
val FRUIT_IMAGE_ID = "fruit_image_id"

class CollapsingToolbarActivity : AppCompatActivity() {

//    CollapsingToolbarLayout should be the direct sub layout of AppBarlayout
//    AppBarlayout should be sub layout of CoordinatorLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collapsing_toolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        collapsing.title = "DEMO"

        val fruitName = intent.getStringExtra(FRUIT_NAME)
        val fruitImageId = intent.getIntExtra(FRUIT_IMAGE_ID, 0)

        collapsing.title = fruitName
        Glide.with(this).load(fruitImageId).into(fruit_image)

        fruit_content.text = fruitName.run {
            val sb = StringBuilder()
            for (i in 1..300) {
                sb.append(this)
            }
            sb.toString()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
