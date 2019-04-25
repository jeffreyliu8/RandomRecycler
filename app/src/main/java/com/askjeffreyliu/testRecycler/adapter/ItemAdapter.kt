package com.askjeffreyliu.testRecycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import com.askjeffreyliu.testRecycler.R
import com.askjeffreyliu.testRecycler.model.Article
import com.squareup.picasso.Picasso

class ItemAdapter :
    RecyclerView.Adapter<MyViewHolder>() {

    companion object {
        const val TYPE_ZERO = 0
        const val TYPE_ONE = 1
        const val TYPE_TWO = 2
    }

    private var mList: List<Article>? = null

    private var typeMap = mutableMapOf<Int, Int>()

    fun updateList(list: List<Article>?) {
        val diffCallback = DiffUtilCallback(mList, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        mList = list
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return when (viewType) {
            TYPE_ZERO -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item1, parent, false)
                MyViewHolder(view)
            }
            TYPE_ONE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item2, parent, false)
                MyViewHolder(view)
            }
            TYPE_TWO -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item3, parent, false)
                MyViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int, payloads: MutableList<Any>) {
//        super.onBindViewHolder(holder, position, payloads)
        onBindViewHolder(holder, position)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(mList!![position])
    }

    override fun getItemViewType(position: Int): Int {
        if (!typeMap.containsKey(position)) {
            typeMap[position] = (TYPE_ZERO..TYPE_TWO).random()
        }
        return typeMap[position]!!
    }
}

interface UpdateViewHolder {
    fun bind(article: Article)
}

class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), UpdateViewHolder {
    private val titleTextView: TextView = view.findViewById(R.id.titleTextView)
    private val summaryTextView: TextView? = view.findViewById(R.id.summaryTextView)
    private val dateTextView: TextView? = view.findViewById(R.id.dateTextView)
    private val imageView: ImageView = view.findViewById(R.id.imageView)

    override fun bind(article: Article) {
        titleTextView.text = article.title
        summaryTextView?.let {
            summaryTextView.text = article.description
        }
        dateTextView?.let {
            dateTextView.text = article.publishedAt
        }
        if (article.urlToImage.isNullOrBlank()) {
            imageView.visibility = View.GONE
        } else {
            imageView.visibility = View.VISIBLE
            Picasso.get().load(article.urlToImage).into(imageView)
        }
    }
}