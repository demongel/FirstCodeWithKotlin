package com.shakespace.firstlinecode.chapter03fragment

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.shakespace.firstlinecode.R
import com.shakespace.firstlinecode.chapter03fragment.bean.News
import com.shakespace.firstlinecode.chapter03fragment.fragment.NewsContentFragment
import com.shakespace.firstlinecode.chapter03fragment.fragment.NewsTitleFragment
import com.shakespace.firstlinecode.global.TAG
import com.shakespace.firstlinecode.global.inTransaction
import com.shakespace.firstlinecode.global.orientation
import kotlinx.android.synthetic.main.activity_news.*

class NewsActivity : AppCompatActivity() {

    var fragment: NewsTitleFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)


//        supportFragmentManager.
        fragment = supportFragmentManager.findFragmentByTag("news") as NewsTitleFragment?
        if (fragment == null) {
            fragment = NewsTitleFragment()
            supportFragmentManager.inTransaction {
                add(R.id.container, fragment!!, "news")
                addToBackStack("news")
            }
        } else {
            supportFragmentManager.inTransaction {
                fragment?.let { show(it) }
            }
            val currentFragment = supportFragmentManager.findFragmentById(R.id.container)
//            val currentFragment = supportFragmentManager.findFragmentByTag("content")
            if (currentFragment != null && currentFragment is NewsContentFragment) {
                supportFragmentManager.inTransaction {
                    hide(currentFragment)
//                    remove(currentFragment)
                }

                /**
                 * if add to backStack , fragment not null after remove , even use  commitNow()
                 * if not,  even remove().commit  can remove completely
                 */
//                supportFragmentManager.beginTransaction().remove(currentFragment).commit()
            }
        }
    }

    fun update(news: News?) {
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            var contentFragment: NewsContentFragment? =
                supportFragmentManager.findFragmentByTag("content") as NewsContentFragment?
            if (contentFragment == null) {
                contentFragment = NewsContentFragment.newInstance(news)
                supportFragmentManager.inTransaction {
                    add(R.id.container, contentFragment, "content")
                    addToBackStack("news")
                }
            } else {
                contentFragment.updateNews(news)
            }
            supportFragmentManager.inTransaction {
                show(contentFragment)
                fragment?.let {
                    Log.d(this.TAG, "update: ----------------")
                    hide(it)
                }
            }

        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            (content_fragment as NewsContentFragment?)
                ?.updateNews(news)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.container)
            if (currentFragment == null) {
                finish()
            }
            if (currentFragment is NewsTitleFragment) {
                supportFragmentManager.inTransaction {
                    show(currentFragment)
                }
            }
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish()
        }
    }

}
