package com.shakespace.firstlinecode.chapter13weather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shakespace.firstlinecode.chapter13weather.db.PlaceRepository

/**
 * created by  shakespace
 * 2019/12/14  23:24
 */
@Suppress("UNCHECKED_CAST")
class AreaChooseViewModelFactory(private val repository: PlaceRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AreaChooseViewModel(repository) as T
    }
}