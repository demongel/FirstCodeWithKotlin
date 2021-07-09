@file:Suppress("DEPRECATION")

package com.shakespace.firstlinecode.chapter13weather.ui

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.shakespace.firstlinecode.App
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.chapter13weather.db.WeatherDao
import com.shakespace.firstlinecode.chapter13weather.db.WeatherRepository
import com.shakespace.firstlinecode.chapter13weather.model.DailyForecast
import com.shakespace.firstlinecode.chapter13weather.model.HeWeather
import com.shakespace.firstlinecode.chapter13weather.network.WeatherNetwork
import com.shakespace.firstlinecode.chapter13weather.service.AutoUpdateService
import com.shakespace.firstlinecode.global.loge
import kotlinx.android.synthetic.main.weather_aqi.*
import kotlinx.android.synthetic.main.weather_forecast.*
import kotlinx.android.synthetic.main.weather_fragment.*
import kotlinx.android.synthetic.main.weather_now.*
import kotlinx.android.synthetic.main.weather_suggestion.*
import kotlinx.android.synthetic.main.weather_title.*


class WeatherFragment : Fragment() {

    private var weatherId: String = ""
        set(value) {
            field = value // use field here
            PreferenceManager.getDefaultSharedPreferences(App.context)
                .edit().putString("weather_id", value).apply()
        }

    companion object {
        fun newInstance(weatherId: String): WeatherFragment {
            val weatherFragment = WeatherFragment()
            weatherFragment.weatherId = weatherId
            return weatherFragment
        }
    }

    private val viewModel: WeatherViewModel by viewModels {
        WeatherViewModelFactory(
            WeatherRepository.getInstance(
                WeatherDao.getInstance(),
                WeatherNetwork.getInstance()
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val window = activity?.window
        window?.let {
            window.clearFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
            )

            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            // this might be important
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
            window.navigationBarColor = Color.TRANSPARENT
        }


        /**
        You should try using this flag as it is designed to remove status bar and navigation.
        --------getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        NOTE: You need to manually clear this while switching or closing fragment. Else this fullscreen will be applicable till the parent activity exists. To do so,
        --------getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        Also, you can use FLAG_FULLSCREEN. Using it will effectively set a transparent notification bar but the icons on the status bar will still show up.
         *
         */
//        activity?.window?.decorView?.systemUiVisibility = (
////                View.SYSTEM_UI_FLAG_LOW_PROFILE or
//                View.SYSTEM_UI_FLAG_FULLSCREEN
//                        or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        or View.SYSTEM_UI_FLAG_IMMERSIVE
//                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                )
//        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        activity?.window?.statusBarColor = Color.TRANSPARENT
        // keep icon on statusbar , but android:fitsSystemWindows="true" not work
//                activity ?. window ?. addFlags (WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        return inflater.inflate(
            R.layout.weather_fragment,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // refresh
        refresh_view.setColorSchemeResources(R.color.colorPrimary)

        nav_button.setOnClickListener {
            drawer_layout.openDrawer(GravityCompat.START)
        }

        viewModel.weatherInfo.observe(this, Observer {
            showWeatherInfo(it)
            loge("----1111 $it")
            refresh_view.isRefreshing = false
        })

        viewModel.bingPicUrl.observe(this, Observer {
            Glide.with(requireContext()).load(it).into(bing_pic_bg)
            refresh_view.isRefreshing = false
        })

        viewModel.error.observe(this, Observer {
            refresh_view.isRefreshing = false
            loge("----2222 $it")
        })

        val preferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

        val weatherInfo = preferences.getString(weatherId, null)
        if (weatherInfo != null) {
            val heWeather = Gson().fromJson(weatherInfo, HeWeather::class.java)
            showWeatherInfo(heWeather)
        } else {
            viewModel.getWeather(weatherId)
        }

        val bingUrl = preferences.getString("bing_pic", "")
        if (bingUrl.isNullOrEmpty()) {
            // load from net
            loadBingPic()
        } else {
            Glide.with(requireContext()).load(bingUrl).into(bing_pic_bg)
        }

        refresh_view.setOnRefreshListener {
            viewModel.refreshWeather(weatherId)
            viewModel.refreshBingPic()
        }

        val updateIntent = Intent(activity, AutoUpdateService::class.java)
        activity?.startService(updateIntent)

    }

    private fun loadBingPic() {
        viewModel.loadBingPic()
    }

    private fun showWeatherInfo(heWeather: HeWeather) {

        // name
        title_city.text = heWeather.basic.city
        // update time
        title_update_time.text = heWeather.basic.update.utc.split(" ")[0]
        // degree
        degree_text.text = heWeather.now.tmp + "℃"
        // weather info
        weather_info_text.text = heWeather.now.cond.txt

        forecast_layout.removeAllViews()
        val action: (DailyForecast) -> Unit = {
            val view =
                layoutInflater.inflate(
                    com.shakespace.firstlinecode.R.layout.weather_forecast_item,
                    forecast_layout,
                    false
                )
            val dateText: TextView = view.findViewById(com.shakespace.firstlinecode.R.id.date_text)
            val infoText: TextView = view.findViewById(com.shakespace.firstlinecode.R.id.info_text)
            val maxText: TextView = view.findViewById(com.shakespace.firstlinecode.R.id.max_text)
            val minText: TextView = view.findViewById(com.shakespace.firstlinecode.R.id.min_text)
            dateText.text = it.date
            infoText.text = it.cond.txt_d
            maxText.text = it.tmp.max
            minText.text = it.tmp.min
            forecast_layout.addView(view)
        }
        heWeather.daily_forecast.forEach(action)

        heWeather.aqi.apply {
            aqi_text.text = heWeather.aqi.city.aqi
            pm25_text.text = heWeather.aqi.city.pm25
        }

        comfort_text.text = "舒适度：" + heWeather.suggestion.comf.txt
        car_wash_text.text = "洗车指数：" + heWeather.suggestion.cw.txt
        sport_text.text = "运动建议：" + heWeather.suggestion.sport.txt


    }

    fun refreshWeather(weatherId: String) {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        }
        this.weatherId = weatherId
        viewModel.refreshWeather(weatherId)
        refresh_view.isRefreshing = true
    }

}
