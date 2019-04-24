package com.askjeffreyliu.testRecycler.adapter

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import com.askjeffreyliu.testRecycler.model.Article

class DiffUtilCallback(private val oldList: List<Article>?, private val newList: List<Article>?) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList?.size ?: 0
    }

    override fun getNewListSize(): Int {
        return newList?.size ?: 0
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList!![oldItemPosition].url == newList!![newItemPosition].url
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val new = newList!![newItemPosition]
        val old = oldList!![oldItemPosition]
        return new == old
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val new = newList!![newItemPosition]
        val old = oldList!![newItemPosition]

        val diff = Bundle()
        if (new !== old) {
            diff.putSerializable(DIFF_KEY, new)
        }
        return if (diff.size() == 0) {
            null
        } else diff
    }

    companion object {
        val DIFF_KEY = "key"
    }
}