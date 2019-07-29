package com.shakespace.firstlinecode.chapter03fragment.fragment


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.chapter03fragment.bean.News
import com.shakespace.firstlinecode.chapter03fragment.fragment.NewsTitleFragment.OnListFragmentInteractionListener
import kotlinx.android.synthetic.main.fragment_news.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class NewsTitleAdapter(
    private val mValues: List<News>
) : RecyclerView.Adapter<NewsTitleAdapter.ViewHolder>() {

    var onClickListener: OnListFragmentInteractionListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_news, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mIdView.text = item.title

        with(holder.mView) {
            tag = item
            if (onClickListener != null) {
                setOnClickListener {
                    onClickListener?.onListFragmentInteraction(item)
                }

            }
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mIdView: TextView = mView.tv_title
    }
}
