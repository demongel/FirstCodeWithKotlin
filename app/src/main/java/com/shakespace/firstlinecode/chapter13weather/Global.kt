package com.shakespace.firstlinecode.chapter13weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * created by  shakespace
 * 2019/12/27  23:26
 */

fun ViewModel.call(success: suspend () -> Unit, fail: suspend (Throwable) -> Unit) =
    viewModelScope.launch {
        try {
            success()
        } catch (t: Throwable) {
            fail(t)
        }
    }


