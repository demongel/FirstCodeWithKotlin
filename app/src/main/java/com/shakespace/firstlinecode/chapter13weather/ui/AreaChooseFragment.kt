package com.shakespace.firstlinecode.chapter13weather.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.chapter13weather.InjectorUtil
import com.shakespace.firstlinecode.chapter13weather.model.City
import com.shakespace.firstlinecode.chapter13weather.model.County
import com.shakespace.firstlinecode.chapter13weather.model.Province
import com.shakespace.firstlinecode.global.loge
import kotlinx.android.synthetic.main.fragment_area_choose.*

/**
 * A simple [Fragment] subclass.
 */
class AreaChooseFragment : Fragment() {

    companion object {
        fun newInstance() = AreaChooseFragment()
    }


    enum class AREA {
        PROVINCE, CITY, COUNTY
    }

    private val viewModel by lazy {
        ViewModelProviders.of(
            this,
            InjectorUtil.getAreaChooseViewModelFactory(requireContext()) // code is too long , use Util to make concise
        ).get(AreaChooseViewModel::class.java)
    }

    var status = AREA.PROVINCE

    private lateinit var adapter: ArrayAdapter<String>

    private var dataList = mutableListOf<String>()
    private var provinces: List<Province>? = null
    private var cities: List<City>? = null
    private var counties: List<County>? = null

    private var selectedProvince: Province? = null
    private var selectedCity: City? = null
    private var selectedCounty: County? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_area_choose, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, dataList)
        list_view.adapter = adapter

        list_view.setOnItemClickListener { parent, view, position, id ->
            when (status) {
                AREA.PROVINCE -> {
                    selectedProvince = provinces?.get(position)
                    queryCities()
                }
                AREA.CITY -> {
                    selectedCity = cities?.get(position)
                    queryCounties()
                }

                AREA.COUNTY -> {
                    val county = counties?.get(position)
                    county?.apply {
                        (activity as WeatherActivity).navigateToWeatherFragment(county.weatherId)
                    }
                }
            }
        }

        iv_back.setOnClickListener {
            handleBackPress()
        }

        viewModel.provinces.observe(this, Observer {
            provinces = it
            dataList.clear()
            it.forEach { province: Province ->
                dataList.add(province.provinceName)
            }
            adapter.notifyDataSetChanged()
            list_view.setSelection(0)
            status = AREA.PROVINCE
            iv_back.visibility = View.GONE
            title.text = "中国"
        })

        viewModel.cities.observe(this, Observer {
            cities = it
            dataList.clear()
            it.forEach { city: City ->
                dataList.add(city.cityName)
            }
            adapter.notifyDataSetChanged()
            list_view.setSelection(0)
            status = AREA.CITY
            iv_back.visibility = View.VISIBLE
        })

        viewModel.counties.observe(this, Observer {
            counties = it
            dataList.clear()
            it.forEach { county: County ->
                dataList.add(county.countyName)
            }
            adapter.notifyDataSetChanged()
            list_view.setSelection(0)
            status = AREA.COUNTY
            iv_back.visibility = View.VISIBLE
        })

        viewModel.error.observe(this, Observer {
            loge("get error  $it")
        })

        viewModel.getProcinces()
        super.onActivityCreated(savedInstanceState)
    }

    private fun queryCounties() {
        val provinceCode = selectedProvince?.provinceCode
        selectedCity?.cityCode?.apply {
            provinceCode?.let {
                viewModel.getCounties(it, this)
            }
        }
        title.text = selectedCity?.cityName
    }

    private fun queryCities() {
        selectedProvince?.provinceCode?.apply {
            viewModel.getCities(this)
        }
        title.text = selectedProvince?.provinceName
    }


    fun handleBackPress(): Boolean {
        return when (status) {
            AREA.PROVINCE -> {
                false
            }
            AREA.CITY -> {
                viewModel.getProcinces()
                true
            }
            AREA.COUNTY -> {
                queryCities()
                true
            }
        }
    }

}
