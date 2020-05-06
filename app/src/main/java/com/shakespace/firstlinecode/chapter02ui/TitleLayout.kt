package com.shakespace.firstlinecode.chapter02ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.shakespace.firstlinecode.global.showToast
import kotlinx.android.synthetic.main.title_layout.view.*


/**
 * // 2019-07-19 NOTE:
 * use @JvmOverloads will create multiple overload method
 * for instance:
 *      class TitleLayout @JvmOverloads constructor(
context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
: LinearLayout(context, attrs, defStyleAttr)


public TitleLayout(Context context) {
this(context, null);
}

public TitleLayout(Context context, @Nullable AttributeSet attrs) {
this(context, attrs, 0);
}

public TitleLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
super(context, attrs, defStyleAttr);

}

but in sometimes , like edittext,button etc, defStyleAttr = 0 will lost focus
 *
 */

class TitleLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    lateinit var view: View

    init {
        init(context)
    }

    private fun init(context: Context) {
        view = LayoutInflater.from(context)
            .inflate(com.shakespace.firstlinecode.R.layout.title_layout, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        iv_back.setOnClickListener {
            context.showToast("click back")
        }

        iv_more.setOnClickListener {
            context.showToast("Click More")
        }
    }
}

