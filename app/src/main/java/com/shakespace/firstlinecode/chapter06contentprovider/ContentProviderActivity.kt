package com.shakespace.firstlinecode.chapter06contentprovider


import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.global.TAG
import com.shakespace.firstlinecode.global.showToast
import kotlinx.android.synthetic.main.activity_content_provider.*

/**
 *
 * ContentProvider
 *  1. use system provider to get or set data
 *  2. create own provider for other app
 *
 *  3. usually , use ContentProvider must by Class ContentResolver (contentResolver)
 *  use insert/update/delete/query to do crud
 *
 *  4. pass a uri to find what you want to write or read
 *
 *  5. a uri by two parts : authority and path
 *      5.1  authority usually use package name to avoid conflict
 *      5.2  path is use to distinguish between different tables
 *      such as
 *      content://com.example.app.provider/table1
 *      content://com.example.app.provider/table2
 *  6. parse this url to uri,then can use contentResolver to query this table
 *           query(
 *                  @RequiresPermission.Read @NonNull Uri uri,
 *                  @Nullable String[] projection,
 *                  @Nullable String selection,
 *                  @Nullable String[] selectionArgs,
 *                  @Nullable String sortOrder)
 *
 *  public final int update(@RequiresPermission.Write @NonNull Uri uri,
 *                          @Nullable ContentValues values,
 *                          @Nullable String where,
 *                          @Nullable String[] selectionArgs)
 *
 *  public final int delete(@RequiresPermission.Write @NonNull Uri url,
 *                          @Nullable String where,
 *                          @Nullable String[] selectionArgs)
 *
 *  public final @Nullable Uri insert(@RequiresPermission.Write @NonNull Uri url,
 *                                  @Nullable ContentValues values)
 *
 *  7. just like use a uri mapping to your database
 *      operation is the same as Database
 */
class ContentProviderActivity : AppCompatActivity() {

    val REQUEST_CODE_PHONE_CALL = 1

    val REQUEST_CODE_CONTACT = 2

    var newId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_provider)
        initListener()


    }

    private fun initListener() {

        insert_data.setOnClickListener {
            val uri = Uri.parse("content://com.shakespace.kotlin.provider/book")
            val values = ContentValues().apply {
                put("name", "First")
                put("author", "Martin")
                put("pages", 500)
                put("price", 55)
            }

            /**
             *  1. get a particular contentProvider by uri
             *  2. then call provider.insert(,,) to insert
             *  3. in this case , will call DataProvider.insert
             */
            contentResolver.insert(uri, values)?.also {
                newId = it.pathSegments[1]
            }

        }

        query_data.setOnClickListener {
            val uri = Uri.parse("content://com.shakespace.kotlin.provider/book")
            val cursor = contentResolver.query(uri, null, null, null, null)

            cursor?.apply {
                while (cursor.moveToNext()) {
                    val name = getString(getColumnIndex("name"))
                    val author = getString(getColumnIndex("author"))
                    val pages = getInt(getColumnIndex("pages"))
                    val price = getDouble(getColumnIndex("price"))

                    Log.e(
                        this.TAG, "result: $name $author $pages $price"
                    )
                }
                close()
            }
        }

        update_data.setOnClickListener {
            newId?.apply {
                val uri = Uri.parse("content://com.shakespace.kotlin.provider/book/$newId")
                val contentValues = ContentValues().apply {
                    put("pages", 800)
                }
                contentResolver.update(uri, contentValues, "id = ?", arrayOf(newId))
            }
        }

        delete_data.setOnClickListener {
            newId?.run {
                val uri = Uri.parse("content://com.shakespace.kotlin.provider/book/$newId")
                contentResolver.delete(uri, "id = ?", arrayOf(newId))
            }
        }

        /**
         * 1. request dangerous permission  when running
         * 1.1 checkPermission whether granted
         * 1.2 if not, ActivityCompat.requestPermissions
         *      context, array with permission name,requestCode
         * 2. check result in onRequestPermissionsResult
         *
         *      PERMISSION_DENIED
         *      PERMISSION_GRANTED
         */
        tv_one.setOnClickListener {
            try {
                if (ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.CALL_PHONE
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.CALL_PHONE),
                        REQUEST_CODE_PHONE_CALL
                    )
                } else {
                    val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:10086"))
                    startActivity(intent)
                }

            } catch (e: Exception) {
                Log.e(this.TAG, "initListener: ${e.message}")
            }

        }


        tv_get_contacts.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_CONTACTS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    REQUEST_CODE_CONTACT
                )
            } else {
                getContact()
            }

        }

    }

    /**
     * // 2019-08-12 NOTE:  ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
     */
    private fun getContact() {
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        cursor?.apply {
            while (cursor.moveToNext()) {
                val displayName = getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val number = getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                Log.e(this.TAG, "contacts: $displayName -- $number ")
            }
        }
        cursor?.close()
    }

    // result in grantResults
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_PHONE_CALL, REQUEST_CODE_CONTACT -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showToast("Granted")
                } else {
                    showToast("rejected")
                }
            }
            else -> showToast("something error")
        }

    }
}
