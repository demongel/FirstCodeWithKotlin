package com.shakespace.firstlinecode.chapter11materialdesign.components

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.shakespace.firstlinecode.R
import kotlinx.android.synthetic.main.activity_toolbar.*

class ToolbarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbar)


        setSupportActionBar(toolbar)

        /**
         * for toolbar title , it will be set as label value in <application/> as default
         * or you can set a label value for current activity
         */


    }

    /**
     * app:showAsAction
     * always : always show as icon
     * ifRomm : if has space to show
     * never :  hide in the menu
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_ui_toolbar, menu)
        return true
    }
}
