package com.shakespace.firstlinecode.chapter04broadcast.practice

class ActivityController {



    companion object{

        private val mutableList:MutableList<BaseActivity> = mutableListOf()

        fun clearAll(){
            mutableList.forEach {
                it.finish()
            }
        }

        fun add(activity: BaseActivity){
            mutableList.add(activity)
        }

        fun remove(activity: BaseActivity){
            mutableList.remove(activity)
        }
    }


}
