package com.shakespace.firstlinecode.chapter06contentprovider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.shakespace.firstlinecode.chapter05persistent.sqlite.DatabaseHelper

class DataProvider : ContentProvider() {
    companion object {

        val BOOK_DIR = 0
        val BOOK_ITEM = 1
        val CATEGORY_DIR = 2
        val CATEGORY_ITEM = 3

        val AUTHORITY = "com.shakespace.kotlin.provider"

        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHORITY, "book", BOOK_DIR)
            uriMatcher.addURI(AUTHORITY, "book/#", BOOK_ITEM)
            uriMatcher.addURI(AUTHORITY, "category", CATEGORY_DIR)
            uriMatcher.addURI(AUTHORITY, "category/#", CATEGORY_ITEM)
        }
    }

    lateinit var dbHelper: DatabaseHelper


    // true if the provider was successfully loaded, false otherwise
    override fun onCreate(): Boolean {
        dbHelper = DatabaseHelper(context, "book.db", null, 2)
        return true
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        var deleteRows: Int = 0
        when (uriMatcher.match(uri)) {
            BOOK_DIR -> {
                deleteRows = dbHelper.writableDatabase.delete("Book", selection, selectionArgs)
            }
            BOOK_ITEM -> {
                val bookId = uri.pathSegments[1]
                deleteRows = dbHelper.writableDatabase.delete("Book", "id = ?", arrayOf(bookId))
            }
            CATEGORY_DIR -> {
                deleteRows = dbHelper.writableDatabase.delete("Category", selection, selectionArgs)
            }
            CATEGORY_ITEM -> {
                val categoryId = uri.pathSegments[1]
                deleteRows =
                    dbHelper.writableDatabase.delete("Category", "id = ?", arrayOf(categoryId))
            }
        }
        return deleteRows
    }

    override fun getType(uri: Uri): String? {

        when (uriMatcher.match(uri)) {
            BOOK_DIR -> {
                return "vnd.android.cursor.dir/vnd.$AUTHORITY.book"
            }
            BOOK_ITEM -> {
                return "vnd.android.cursor.item/vnd.$AUTHORITY.book"
            }
            CATEGORY_DIR -> {
                return "vnd.android.cursor.dir/vnd.$AUTHORITY.category"
            }
            CATEGORY_ITEM -> {
                return "vnd.android.cursor.item/vnd.$AUTHORITY.category"
            }
            else -> return null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        var uriReturn: Uri? = null
        when (uriMatcher.match(uri)) {
            BOOK_DIR, BOOK_ITEM -> {
                val newId = dbHelper.writableDatabase.insert("Book", null, values)
                uriReturn = Uri.parse("content://$AUTHORITY/book/$newId")
            }
            CATEGORY_DIR, CATEGORY_ITEM -> {
                val newId = dbHelper.writableDatabase.insert("Category", null, values)
                uriReturn = Uri.parse("content://$AUTHORITY/category/$newId")
            }
        }
        return uriReturn
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {

        var cursor: Cursor? = null
        /**
         *         match will return code
         *      The code for the matched node (added using addURI),
         *      or -1 if there is no matched node
         */
        when (uriMatcher.match(uri)) {
            BOOK_DIR -> cursor = dbHelper.readableDatabase.query(
                "Book", projection, selection, selectionArgs, null
                , null, sortOrder
            )

            BOOK_ITEM -> {
                val bookId = uri.pathSegments[1]
                cursor = dbHelper.readableDatabase.query(

                    "Book", projection, "id = ?", arrayOf(bookId), null
                    , null, sortOrder
                )
            }
            CATEGORY_DIR -> {
                cursor = dbHelper.readableDatabase.query(
                    "Category", projection, selection, selectionArgs, null
                    , null, sortOrder
                )
            }

            CATEGORY_ITEM -> {
                val categoryId = uri.pathSegments[1]
                cursor = dbHelper.readableDatabase.query(
                    "Category", projection, "id = ?", arrayOf(categoryId), null
                    , null, sortOrder
                )
            }

        }

        return cursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        var updateRows: Int = 0
        when (uriMatcher.match(uri)) {
            BOOK_DIR -> {
                updateRows =
                    dbHelper.writableDatabase.update("Book", values, selection, selectionArgs)
            }
            BOOK_ITEM -> {
                val bookId = uri.pathSegments[1]
                updateRows =
                    dbHelper.writableDatabase.update("Book", values, "id = ?", arrayOf(bookId))
            }
            CATEGORY_DIR -> {
                updateRows =
                    dbHelper.writableDatabase.update("Category", values, selection, selectionArgs)
            }
            CATEGORY_ITEM -> {
                val categoryId = uri.pathSegments[1]
                updateRows = dbHelper.writableDatabase.update(
                    "Category",
                    values,
                    "id = ?",
                    arrayOf(categoryId)
                )
            }
        }
        return updateRows
    }
}
