package com.shakespace.firstlinecode.chapter06contentprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

/**
 * create own contentProvider
 *
 *  content://com.example.app.provider/table1   : access  table1
 *  content://com.example.app.provider/table1/1 : access  data id = 1  in table1
 *
 *  content://com.example.app.provider/*       : access ALL
 *  content://com.example.app.provider/#       : access any row
*/
 * */

class MyProvider : ContentProvider() {

    //  see UriMaticher code
    val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    // only will be called when ContentResolver want to access data in this app
    override fun onCreate(): Boolean {
        return false
    }

    /**
     * uri :  determine which table to access
     */
    override fun query(
        uri: Uri,
        projection: Array<String>?,
        selection: String?,
        selectionArgs: Array<String>?,
        sortOrder: String?
    ): Cursor? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    /**
     *  start with vnd
     *  end with vnd.<authority>.<path>
     *
     *  if uri end with path , append android.cursor.dir/
     *  if uri end with id , appedn android.cursor.item/
     *      like:
     *          content://com.example.app.provider/table1
     *      type is
     *          vnd.android.cursor.dir/vnd.com.example.app.provider.table1
     *
     *      and :
     *          content://com.example.app.provider/table1/1
     *      type is
     *          vnd.android.cursor.item/vnd.com.example.app.provider.table1
     */
    override fun getType(uri: Uri): String? {
        return null
    }

}

