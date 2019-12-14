package com.shakespace.firstlinecode.chapter03fragment.fragment


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.global.TAG


/**
 * A simple [Fragment] subclass.
 *
 */
class InnerFragment : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach: ")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(this.TAG, "onCreate: ")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(this.TAG, "onCreateView: ")
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inner, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(this.TAG, "onViewCreated: ")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(this.TAG, "onActivityCreated: ")
    }

    override fun onStart() {
        super.onStart()
        Log.d(this.TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(this.TAG, "onResume: ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(this.TAG, "onStop: ")
    }

    override fun onPause() {
        super.onPause()
        Log.d(this.TAG, "onPause: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(this.TAG, "onDestroy: ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(this.TAG, "onDestroyView: ")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(this.TAG, "onDetach: ")
    }

}
/*
2019-07-24 20:51:28.654   : onAttach:
2019-07-24 20:51:28.654   : onCreate:
2019-07-24 20:51:28.657   : onCreateView:
2019-07-24 20:51:28.679   : onViewCreated:
2019-07-24 20:51:28.680   : onActivityCreated:
2019-07-24 20:51:28.680   : onStart:
2019-07-24 20:51:28.681   : onResume:
2019-07-24 20:51:30.116   : onPause:
2019-07-24 20:51:30.117   : onStop:
2019-07-24 20:51:30.118   : onDestroyView:
2019-07-24 20:51:30.126   : onDestroy:
2019-07-24 20:51:30.126   : onDetach:

 */
