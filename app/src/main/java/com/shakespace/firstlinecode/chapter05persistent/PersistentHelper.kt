@file:Suppress("UNUSED_VARIABLE")

package com.shakespace.firstlinecode.chapter05persistent

import android.content.Context
import android.content.SharedPreferences
import java.io.BufferedReader
import java.io.File


object PersistentHelper {


    //  write to internal
    fun saveToFile(fileName: String, msg: String, context: Context) {

        // write to file
        // path : /data/data/com.shakespace.firstlinecode/files
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(msg.toByteArray())
        }

        var reader: BufferedReader
        val builder = StringBuilder()

    }

    fun readFromFile(fileName: String, context: Context): String {
        return try {
            val file = File(context.filesDir, fileName)
            file.readText()
        } catch (e: Exception) {
            ""
        }

    }

    private const val SP_NAME = "config"
    private var MODE = Context.MODE_PRIVATE
    lateinit var sp: SharedPreferences

    fun init(context: Context, name: String = SP_NAME, mode: Int = MODE): PersistentHelper {
        sp = context.getSharedPreferences(name, mode)
        return this
    }

    fun saveValue(key: String, value: Any) = with(sp.edit()) {
        when (value) {
            is Int -> putInt(key, value)
            is String -> putString(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            is Boolean -> putBoolean(key, value)
            else -> throw IllegalArgumentException("type error")
        }.apply()
    }


    @Suppress("IMPLICIT_CAST_TO_ANY", "UNCHECKED_CAST")
    fun <T> getValue(key: String, default: T): T = with(sp) {
        return when (default) {
            is Int -> getInt(key, default)
            is String -> getString(key, default)
            is Long -> getLong(key, default)
            is Float -> getFloat(key, default)
            is Boolean -> getBoolean(key, default)
            else -> default
        } as T

    }

}

/*
old way
       try {
            val output = context.openFileOutput(fileName, Context.MODE_PRIVATE)
            val write = BufferedWriter(OutputStreamWriter(output))
            write.write(msg)
        } catch (e: Exception) {
        } finally {

        }

        val data = "data"
        var out: FileOutputStream?
        var writer: BufferedWriter? = null
        try {
            out = openFileOutput("data", Context.MODE_PRIVATE)
            writer = BufferedWriter(OutputStreamWriter(out))
            writer.write(data)
            writer.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                writer?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
 */

/*
 try
        {
            reader = BufferedReader(FileReader(fileName))
            var line = ""
            while ((reader.readLine().also {
                    line = it
                }) != null) {
                builder.append(line)
            }
        }
        catch (e: FileNotFoundException)
        {
            e.printStackTrace()
        }
        catch (e: IOException)
        {
            e.printStackTrace()
        }

 */