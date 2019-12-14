package com.shakespace.firstlinecode.chapter13weather.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.chapter13weather.InjectorUtil
import com.shakespace.firstlinecode.global.loge
import kotlinx.android.synthetic.main.fragment_area_choose.*

/**
 * A simple [Fragment] subclass.
 */
class AreaChooseFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProviders.of(
            this,
            InjectorUtil.getAreaChooseViewModelFactory(requireContext()) // code is too long , use Util to make concise
        ).get(AreaChooseViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_area_choose, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        viewModel.provinces.observe(this, Observer {
            loge(Gson().toJson(it))
            title.text = it.get(0).provinceName
        })

        viewModel.error.observe(this, Observer {
            loge("get error  $it")
        })
        viewModel.getProcinces()
        super.onActivityCreated(savedInstanceState)
    }


}
