package com.shakespace.firstlinecode.chapter11materialdesign.components

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.snackbar.Snackbar
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.global.showToast
import kotlinx.android.synthetic.main.activity_darwer_layout.*

class DrawerLayoutActivity : AppCompatActivity() {

    /**
     * DrawerLayout need two sub view
     * first is main view
     * second is for the drawer
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_darwer_layout)

        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp)

//      use below code is more convenience
        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        )
        toggle.syncState()
        drawer_layout.addDrawerListener(toggle)

        /**
         * use fab with CoordinateLayout , will be more powerful
         *
         * if use CoordinateLayout , in this function  ,
         * the first View ,should be in CoordinateLayout
         * or CoordinateLayout can't coordinate fab and snackbar
         */
        fab.setOnClickListener {
            Snackbar.make(fab, "click FAB", Snackbar.LENGTH_SHORT)
                .setAction("UNDO") {
                    showToast("UNDO")
                }.show()
        }
    }

    /**
     *  the button on the topleft of toolbar named HomeAsUp
     *  and the id of this button is android.R.id.home
     *  below code :
     *   supportActionBar?.setDisplayHomeAsUpEnabled(true)
     *    supportActionBar?.setHomeAsUpIndicator(R.drawable.notification)
     *  will change the icon of HomeAsUp
     */
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> drawer_layout.openDrawer(GravityCompat.START)
        }
        return true
    }
}
