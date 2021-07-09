package com.shakespace.firstlinecode.chapter03fragment.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.chapter03fragment.bean.News
import kotlinx.android.synthetic.main.news_content_fragment.*


/**
 * A simple [Fragment] subclass.
 *
 */
class NewsContentFragment : Fragment() {

    private var news: News? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.news_content_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (news != null) {
            tv_title.text = news?.title
            tv_content.text = news?.content
        }
    }

    fun updateNews(news: News?) {
        this.news = news
        if (news != null) {
            tv_title.text = news.title
            tv_content.text = news.content
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(news: News?) =
            NewsContentFragment().apply {
                this.news = news
            }
    }


}
