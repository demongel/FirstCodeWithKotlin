package com.shakespace.firstlinecode.chapter02ui.bean


const val TYPE_SEND = 0
const val TYPE_RECEIVE = 1

data class ChatMsg(val msg: String, val type: Int)
