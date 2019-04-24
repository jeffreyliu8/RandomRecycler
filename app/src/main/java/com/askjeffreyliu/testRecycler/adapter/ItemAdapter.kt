package com.askjeffreyliu.testRecycler.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.askjeffreyliu.testRecycler.R
import com.askjeffreyliu.testRecycler.model.Article
import com.squareup.picasso.Picasso

class ItemAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_ZERO = 0
        const val TYPE_ONE = 1
        const val TYPE_TWO = 2
    }

    private var mList: List<Article>? = null

    private var typeMap = mutableMapOf<Int, Int>()

    fun updateList(list: List<Article>?) {
        mList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ZERO -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item1, parent, false)
                Type1ViewHolder(view)
            }
            TYPE_ONE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item2, parent, false)
                Type2ViewHolder(view)
            }
            TYPE_TWO -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item3, parent, false)
                Type3ViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val element = mList!![position]
        when (holder) {
            is Type1ViewHolder -> holder.bind(element)
            is Type2ViewHolder -> holder.bind(element)
            is Type3ViewHolder -> holder.bind(element)
            else -> throw IllegalArgumentException()
        }
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

class Type1ViewHolder(view: View) : RecyclerView.ViewHolder(view), UpdateViewHolder {
    private val titleTextView: TextView = view.findViewById(R.id.titleTextView)
    private val summaryTextView: TextView = view.findViewById(R.id.summaryTextView)
    private val dateTextView: TextView = view.findViewById(R.id.dateTextView)
    private val imageView: ImageView = view.findViewById(R.id.imageView)

    override fun bind(article: Article) {
        titleTextView.text = article.title
        summaryTextView.text = article.description
        dateTextView.text = article.publishedAt
        if (article.urlToImage.isNullOrBlank()) {
            imageView.visibility = View.GONE
        } else {
            imageView.visibility = View.VISIBLE
            Picasso.get().load(article.urlToImage).into(imageView)
        }
    }
}

class Type2ViewHolder(view: View) : RecyclerView.ViewHolder(view), UpdateViewHolder {
    private val titleTextView: TextView = view.findViewById(R.id.titleTextView)
    private val summaryTextView: TextView = view.findViewById(R.id.summaryTextView)
    private val imageView: ImageView = view.findViewById(R.id.imageView)

    override fun bind(article: Article) {
        titleTextView.text = article.title
        summaryTextView.text = article.description
        if (article.urlToImage.isNullOrBlank()) {
            imageView.visibility = View.GONE
        } else {
            imageView.visibility = View.VISIBLE
            Picasso.get().load(article.urlToImage).into(imageView)
        }
    }
}

class Type3ViewHolder(view: View) : RecyclerView.ViewHolder(view), UpdateViewHolder {
    private val titleTextView: TextView = view.findViewById(R.id.titleTextView)
    private val imageView: ImageView = view.findViewById(R.id.imageView)

    override fun bind(article: Article) {
        titleTextView.text = article.title
        if (article.urlToImage.isNullOrBlank()) {
            imageView.visibility = View.GONE
        } else {
            imageView.visibility = View.VISIBLE
            Picasso.get().load(article.urlToImage).into(imageView)
        }
    }
}