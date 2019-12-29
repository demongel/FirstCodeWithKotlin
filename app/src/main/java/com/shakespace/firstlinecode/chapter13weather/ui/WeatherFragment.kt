package com.shakespace.firstlinecode.chapter13weather.ui

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.shakespace.firstlinecode.App
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.chapter13weather.db.WeatherDao
import com.shakespace.firstlinecode.chapter13weather.db.WeatherRepository
import com.shakespace.firstlinecode.chapter13weather.model.DailyForecast
import com.shakespace.firstlinecode.chapter13weather.model.HeWeather
import com.shakespace.firstlinecode.chapter13weather.network.WeatherNetwork
import com.shakespace.firstlinecode.global.loge
import kotlinx.android.synthetic.main.weather_aqi.*
import kotlinx.android.synthetic.main.weather_forecast.*
import kotlinx.android.synthetic.main.weather_now.*
import kotlinx.android.synthetic.main.weather_suggestion.*
import kotlinx.android.synthetic.main.weather_title.*

class WeatherFragment : Fragment() {

    private var weatherId: String = ""

    companion object {
        fun newInstance(weatherId: String): WeatherFragment {
            val weatherFragment = WeatherFragment()
            weatherFragment.weatherId = weatherId
            return weatherFragment
        }
    }

    private lateinit var viewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(
            this, WeatherViewModelFactory(
                WeatherRepository.getInstance(
                    WeatherDao.getInstance(),
                    WeatherNetwork.getInstance()
                )
            )
        ).get(WeatherViewModel::class.java)



        viewModel.weatherInfo.observe(this, Observer {
            showWeatherInfo(it)
            loge("----1111 $it")
        })

        viewModel.error.observe(this, Observer {
            loge("----2222 $it")
        })

        val weatherInfo =
            PreferenceManager.getDefaultSharedPreferences(App.context)
                .getString(weatherId, null)
        if (weatherInfo != null) {
            val heWeather = Gson().fromJson(weatherInfo, HeWeather::class.java)
            showWeatherInfo(heWeather)
        } else {
            viewModel.getWeather(weatherId)
        }

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
                layoutInflater.inflate(R.layout.weather_forecast_item, forecast_layout, false)
            val dateText: TextView = view.findViewById(R.id.date_text)
            val infoText: TextView = view.findViewById(R.id.info_text)
            val maxText: TextView = view.findViewById(R.id.max_text)
            val minText: TextView = view.findViewById(R.id.min_text)
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

}
