package com.shakespace.firstlinecode.chapter13weather

import android.content.Context
import com.shakespace.firstlinecode.chapter13weather.db.AppDatabase
import com.shakespace.firstlinecode.chapter13weather.db.PlaceRepository
import com.shakespace.firstlinecode.chapter13weather.network.PlaceNetwork
import com.shakespace.firstlinecode.chapter13weather.ui.AreaChooseViewModelFactory

/**
 * created by  shakespace
 * 2019/12/14  22:27
 */
object InjectorUtil {


    fun getAreaChooseViewModelFactory(context: Context): AreaChooseViewModelFactory {
        val repository = getPlaceRepository(context)
        return AreaChooseViewModelFactory(repository)
    }

    fun getPlaceRepository(context: Context): PlaceRepository {

        return PlaceRepository.getInstance(
            AppDatabase.getInstance(context).placeDao(),
            PlaceNetwork.getInstance()
        )

    }
}