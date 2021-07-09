package com.shakespace.firstlinecode.chapter01activity


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.global.TAG
import com.shakespace.firstlinecode.kotlin.bean.Person
import kotlinx.android.synthetic.main.activity_first.*
import kotlin.reflect.jvm.jvmName


// singleTask
@Suppress("UNUSED_VARIABLE", "LocalVariableName", "JAVA_CLASS_ON_COMPANION")
class FirstActivity : AppCompatActivity() {

    var toast: Toast? = null

    val launcher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result -> showToast(" Result is ${result?.data?.getStringExtra("result")}") }

    val launcherPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            showToast(" Result is $it")
        }

    val launcher2 = registerForActivityResult(object : ActivityResultContract<Boolean, String>() {
        override fun createIntent(context: Context, input: Boolean?): Intent {
            // input 是 launcher.launch 里传入的值
            return Intent(context, SecondActivity::class.java)
        }

        override fun parseResult(resultCode: Int, intent: Intent?): String {
            // 拿到结果转换成目标格式
            return intent.toString()
        }

    }, object : ActivityResultCallback<String> {
        override fun onActivityResult(result: String?) {
            showToast(" Result is $result")
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        registerForContextMenu(textView)

        initListener()

    }

    private fun initListener() {

        /**
         * use implicit intent
         * 1. need action
         * 2. need addCategory
         * 3. must have both ,and at least have  <category android:name="android.intent.category.DEFAULT" /> in manifest
         */
        tv_to_second.setOnClickListener {
            try {
                val intent = Intent()
                intent.action = "android.intent.action.CUSTOM"
                intent.addCategory("android.intent.category.CUSTOM")
                intent.putExtra("data", "From First")
                startActivity(intent)
            } catch (e: Exception) {
                Log.e(this.TAG, e.message!!)
            }
        }

        tv_dial.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:10086")
            startActivity(intent)
        }


        tv_for_result.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            launcher.launch(intent)
//            launcherPermission.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//            launcher2.launch(true)
//            startActivityForResult(intent, 0x01)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu?.add(0, 1, 0, "Add")
        menu?.add(0, 2, 0, "Share")
        menu?.add(0, 3, 0, "Count")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            1 -> showToast("add")
            2 -> showToast("share")
            3 -> showToast("quit")
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.add -> {
                showToast("show")
                showType()
            }
            R.id.share -> showToast("share")
            R.id.quit -> showToast("quit")
        }

        return super.onOptionsItemSelected(item)
    }

    fun showToast(msg: String) {
        if (toast == null) {
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
        } else {
            toast?.setText(msg)
        }
        toast?.show()
    }


    /**
    so , Person::class  will get KClass , and .java will change to java class
    person::class also can get KClass , but has type projecttion(类型投射，泛型中只能是Person的子类)

    if want to get javaClass
    1. use   Person::class.java
    2. use  person.javaClass

     */
    // 2019-07-17 NOTE:  to show local variable type hint
    //  try Setting -- editor --Appearance -- show parameter name hints
    // Cofigure... -- choose kotlin -- tick all in the bottom
    fun showType() {
        val person = Person()
        val stringBuilder = StringBuilder()

        val PersonClass = Person::class
        val PersonClassInJava = Person::class.java

        val personClass = person::class
        val personClassInJava = person::class.java

        //  get rumtime type , sometimes error
        val PersonJavaClass = Person::javaClass
//        val personJavaClass = person::javaClass

        // 2019-07-17 NOTE:  if Person dont have companion object, could not call Person.javaClass
        // and one more, Person.javaClass will get type of companion
        val PersonDotJavaClass = Person.javaClass
        val personDotJavaClass = person.javaClass

        val kotlin1 = Person.javaClass.kotlin
        val kotlin = Person::class.java.kotlin.java.kotlin.java.kotlin.java


        // PersonClass.simpleName and personClass.simpleName need reflect support
        stringBuilder.append(
            "Person::class == ${PersonClass.jvmName} \n" +
                    "Person::class.java == ${PersonClassInJava.typeName}\n" +
                    "person::class == ${personClass.jvmName}\n" +
                    "person::class.java == ${personClassInJava.typeName}\n" +
                    "Person::javaClass == ${PersonJavaClass.name}\n" +
//                    "person::javaClass ==  ${personJavaClass.name}\n" +
                    "Person.javaClass == ${PersonDotJavaClass.typeName}\n" +
                    "person.javaClass ==  ${personDotJavaClass.typeName}"
        )

        tv_display.setText(stringBuilder.toString())
    }

}
