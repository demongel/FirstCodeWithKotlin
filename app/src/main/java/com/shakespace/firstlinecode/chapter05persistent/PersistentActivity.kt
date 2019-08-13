package com.shakespace.firstlinecode.chapter05persistent

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.chapter05persistent.sqlite.DatabaseHelper
import com.shakespace.firstlinecode.global.TAG
import com.shakespace.firstlinecode.global.showToast
import kotlinx.android.synthetic.main.activity_persistent.*

class PersistentActivity : AppCompatActivity() {

    lateinit var helper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_persistent)
        helper = DatabaseHelper(this, "book.db", null, 2)


        initListener()
    }

    private fun initListener() {

        retrieve_data.setOnClickListener {
            /**
             * String table     :  table name
             * String[] columns :   columns name like {"name","author"}
             * String selection :   conditions name
             * String[] selectionArgs   :  conditions value
             * String groupBy   :       such as group by  "author"
             * String having    :       such as  author = "Martin"
             * String orderBy   :   order
             *
             */
            val cursor = helper.writableDatabase.query("Book", null, null, null, null, null, null)

            /**
             * will find log as follow
            2019-08-12 02:06:48.047 14753-14753/com.shakespace.firstlinecode E/PersistentActivity: result: Code shakespace 332 50.0
            2019-08-12 02:06:48.047 14753-14753/com.shakespace.firstlinecode I/chatty: uid=10135(com.shakespace.firstlinecode) identical 4 lines
            2019-08-12 02:06:48.047 14753-14753/com.shakespace.firstlinecode E/PersistentActivity: result: Code shakespace 332 50.0

             when log mutiply identical message  ,msg will be combine.
             (seems 5 lines per second)
             */
            if (cursor.moveToFirst()) {
                do {
                    val name = cursor.getString(cursor.getColumnIndex("name"))
                    val author = cursor.getString(cursor.getColumnIndex("author"))
                    val pages = cursor.getInt(cursor.getColumnIndex("pages"))
                    val price = cursor.getDouble(cursor.getColumnIndex("price"))

                    Log.e(
                        this.TAG, "result: $name $author $pages $price"
                    )

                } while (cursor.moveToNext())
            }
            cursor.close()

        }

        insert_data.setOnClickListener {

            val values = ContentValues().apply {
                put("name", "Code")
                put("author", "shakespace")
                put("pages", 332)
                put("price", 35)
            }
            /**
             *  1. table name
             *  2. usually use null
             *  3. contentValues which contains key-value to insert
             */
            val result = helper.writableDatabase.insert("Book", null, values)

            if (result != -1L) {
                showToast("insert success")
            }
        }

        update_data.setOnClickListener {
            val values = ContentValues().apply {
                put("price", 50)
            }
            /**
             *  3. where : which condition for limited
             *  4. limited condition(s) value
             */
            val result = helper.writableDatabase.update("Book", values, "name = ?", arrayOf("Code"))
            showToast("update row $result")
        }

        delete_data.setOnClickListener {
            val result = helper.writableDatabase.delete("Book", "price > ?", arrayOf("40"))
            showToast("delete rows $result")
        }

        /**
         *  toast only show one time
         *  first time ,db "book.db" is't exist,so call create
         *  then , click again, book.db has created , create method will not called again.
         */
        create_database.setOnClickListener {

            // must call this or readableDatabase
            // or , db file will not be created
            helper.writableDatabase
        }

        write2sp.setOnClickListener {
            val text = data.text.toString().trim()
            if (text.isNotEmpty()) {
                PersistentHelper.init(this).saveValue("name", text)
                showToast("save success!")
                data.setText("")
            } else {
                showToast("please input something")
            }
        }

        read_from_sp.setOnClickListener {
            val value = PersistentHelper.init(this).getValue("name", "nothing")
            data.setText(value)
        }


        write2file.setOnClickListener {
            val text = data.text.toString().trim()
            if (text.isNotEmpty()) {
                PersistentHelper.saveToFile("data", text, this)
                showToast("save success!")
                data.setText("")
            } else {
                showToast("please input something")
            }
        }

        read_from_file.setOnClickListener {

            val text = PersistentHelper.readFromFile("data", this)
            if (text.isNotEmpty()) {
                data.setText(text)
                showToast("read success!")
            } else {
                showToast("file not found")
            }
        }


    }
}

/**
 *  Three ways to get Sharedpreference
 *  1. this.getSharedPreferences("name", Context.MODE_PRIVATE)
 *      this is a Context
sp file will be stored to /data/data/<package name>/shared_prefs/<file name>
MODE_PRIVATE = 0
others mode like  MODE_WORLD_READABLE/MODE_WORLD_WRITEABLE  has Deprecated

 *  2. this.getPreferences(Context.MODE_PRIVATE)
 *      this is an Activity
 *      will use getLocalClassName() as file name
 *
 *
 * 3.   PreferenceManager.getDefaultSharedPreferences(this)
 *      file name will be  context.getPackageName() + "_preferences"
 *
 * use like
 *      this.getPreferences(Context.MODE_PRIVATE).edit().putInt("name",11).apply()
 */
