package com.shakespace.firstlinecode.chapter10LBS

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.map.MapStatusUpdateFactory
import com.baidu.mapapi.map.MyLocationData
import com.baidu.mapapi.model.LatLng
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.global.showToast
import kotlinx.android.synthetic.main.activity_lbs.*

@Suppress("DEPRECATION")
class LBSActivity : AppCompatActivity() {

    private lateinit var locationClient: LocationClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lbs)

        locationClient = LocationClient(applicationContext)
        locationClient.registerLocationListener { bdLocation ->
            // please open gps service , or will return 4.9E-324
            tv_display.text =
                "latitude = ${bdLocation?.latitude} , longitude =  ${bdLocation?.longitude}"

            // translate to address

            val address = StringBuilder()
            address.append(
                "country : ${bdLocation?.country} \n " +
                        "province : ${bdLocation?.province} \n" +
                        "city : ${bdLocation?.city} \n" +
                        "district : ${bdLocation?.district} \n" +
                        "street : ${bdLocation?.street}"
            )

            tv_display.text = tv_display.text.toString() + "\n" + address.toString()

            val myLocation = MyLocationData.Builder()
            val latLng = bdLocation?.let {
                myLocation.latitude(it.latitude)
                myLocation.longitude(it.longitude)
                LatLng(it.latitude, it.longitude)
            }

            val newLatLng = MapStatusUpdateFactory.newLatLng(latLng)
            // move to your location
            map_view.map.animateMapStatus(newLatLng)

            // show me on map by myLocation
            map_view.map.setMyLocationData(myLocation.build())
        }

        getPermission()

        tv_start.setOnClickListener {
            requestLocation()
        }

        val baiduMap = map_view.map
        baiduMap.animateMapStatus(MapStatusUpdateFactory.zoomTo(12.5F))
        // can show my locaion
        baiduMap.isMyLocationEnabled = true
    }

    private fun getPermission() {
        val mutableList = mutableListOf<String>()

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            mutableList.add(Manifest.permission.READ_PHONE_STATE)
        }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            mutableList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            mutableList.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        if (mutableList.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, mutableList.toTypedArray(), 1)
        }

    }

    private fun requestLocation() {
        initLocation()
        locationClient.start()
    }

    // set some location options
    private fun initLocation() {
        val option = LocationClientOption()
        // scan every 5000ms
        option.setScanSpan(5000)
        option.setIsNeedAddress(true)
        locationClient.locOption = option
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty()) {
                    grantResults.forEach {
                        if (it != PackageManager.PERMISSION_GRANTED) {
                            showToast("please allow all permission request , or can't use this program")
                            finish()
                            return
                        }
                    }
                } else {
                    showToast("unknown issue")
                    finish()
                }
            }
            else -> ""
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onResume() {
        super.onResume()
        map_view.onResume()
    }

    override fun onPause() {
        super.onPause()
        map_view.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        locationClient.stop()
        map_view.onDestroy()
        map_view.map.isMyLocationEnabled = false
    }


}
