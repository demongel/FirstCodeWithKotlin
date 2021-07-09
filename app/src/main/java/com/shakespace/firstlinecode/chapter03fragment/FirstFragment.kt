package com.shakespace.firstlinecode.chapter03fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.global.TAG
import kotlinx.android.synthetic.main.fragment_first.*

class FirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        to_second.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.container, SecondFragment(), SecondFragment::class.java.name)
                .addToBackStack("stack2").commit()
        }
    }

    override fun onPause() {
        super.onPause()
        Log.e(this.TAG, "onPause:  ")
    }

    override fun onStop() {
        super.onStop()
        Log.e(this.TAG, "onStop:  ")
    }

    override fun onResume() {
        super.onResume()
        Log.e(this.TAG, "onResume:  ")
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Log.e(this.TAG, "onHiddenChanged: $hidden ")
    }


}