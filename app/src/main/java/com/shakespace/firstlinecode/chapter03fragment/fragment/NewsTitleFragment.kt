package com.shakespace.firstlinecode.chapter03fragment.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.chapter03fragment.NewsActivity
import com.shakespace.firstlinecode.chapter03fragment.bean.News
import com.shakespace.firstlinecode.chapter03fragment.fragment.dummy.DummyContent

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [NewsTitleFragment.OnListFragmentInteractionListener] interface.
 */
class NewsTitleFragment : Fragment() {


    private var listener: OnListFragmentInteractionListener? = null
    private var adapter: NewsTitleAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news_list, container, false)

        adapter = NewsTitleAdapter(DummyContent.ITEMS)
        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(activity)
                this.adapter = this@NewsTitleFragment.adapter
            }
        }
        // seems not a good way
        adapter?.onClickListener = object :OnListFragmentInteractionListener{
            override fun onListFragmentInteraction(item: News?) {
                (activity as NewsActivity).update(item)
            }
        }

        return view
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(item: News?)
    }

}
