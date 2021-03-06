package com.shakespace.firstlinecode.chapter11materialdesign

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.chapter11materialdesign.components.*
import com.shakespace.firstlinecode.global.start
import kotlinx.android.synthetic.main.activity_material_design.*

class MaterialDesignActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_design)

//        setSupportActionBar(toolbar)

        initListener()
    }

    private fun initListener() {

        tv_toolbar.setOnClickListener {
            start(ToolbarActivity::class.java)
        }

        tv_drawer_layout.setOnClickListener {
            start(DrawerLayoutActivity::class.java)
        }

        tv_app_bar.setOnClickListener {
            start(AppBarActivity::class.java)
        }

        coordinator.setOnClickListener {
            start(CoordinatorActivity::class.java)
        }

        collapsing.setOnClickListener {
            start(CollapsingToolbarActivity::class.java)
        }
    }
}
