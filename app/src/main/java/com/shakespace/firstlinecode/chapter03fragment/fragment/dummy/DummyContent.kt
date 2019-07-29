package com.shakespace.firstlinecode.chapter03fragment.fragment.dummy

import com.shakespace.firstlinecode.chapter03fragment.bean.News
import java.util.*

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<News> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, News> = HashMap()

    private val COUNT = 25

    init {
        // Add some sample items.
        for (i in 1..COUNT) {
            addItem(createDummyItem(i))
        }
    }

    private fun addItem(item: News) {
        ITEMS.add(item)
        ITEM_MAP.put(item.title, item)
    }

    private fun createDummyItem(position: Int): News {
        return News(position.toString(), "Item $position")
    }

}
