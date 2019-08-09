package com.shakespace.firstlinecode.chapter05persistent.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.shakespace.firstlinecode.global.showToast


/**
 * [name] : for database
 * [factory] :  usually use null
 * [version] : for current database
 *
 * can use these two methods to operator database
 *      getWritableDatabase()
 *      getReadableDatabase
 *
 *  database file  will be in data/data/<package name>/database/
 *
 *
 *  SQL : Structured Query Language
 */

class DatabaseHelper(
    val context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {


    val sql = "create table Book(" +
            "id integer primary key autoincrement," +
            "author text," +
            "price real," +
            "pages integer," +
            "name text)"

    val sql2 = "create table Category(" +
            "id integer primary key autoincrement," +
            "category_name text," +
            "category_code integer)"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(sql)
        db?.execSQL(sql2)
        context?.showToast("create database success")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists Book")
        db?.execSQL("drop table if exists Category")
        onCreate(db)
    }


}