package com.shakespace.firstlinecode.chapter11materialdesign

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.chapter11materialdesign.components.CollapsingToolbarActivity
import com.shakespace.firstlinecode.chapter11materialdesign.components.CoordinatorActivity
import com.shakespace.firstlinecode.global.start
import kotlinx.android.synthetic.main.activity_material_design.*

class MaterialDesignActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_design)

        initListener()
    }

    private fun initListener() {

        coordinator.setOnClickListener {
            start(CoordinatorActivity::class.java)
        }

        collapsing.setOnClickListener {
            start(CollapsingToolbarActivity::class.java)
        }
    }
}
