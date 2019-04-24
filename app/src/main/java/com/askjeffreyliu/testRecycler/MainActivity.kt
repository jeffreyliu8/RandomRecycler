package com.askjeffreyliu.testRecycler

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.askjeffreyliu.testRecycler.adapter.ItemAdapter
import com.askjeffreyliu.testRecycler.model.Article
import com.askjeffreyliu.testRecycler.viewmodel.MainViewModel

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mAdapter: ItemAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        setupViewModel()
    }

    private fun setupRecyclerView() {
        // Creates a vertical Layout Manager
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Access the RecyclerView Adapter
        mAdapter = ItemAdapter()
        recyclerView.adapter = mAdapter

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.getNewsFromDb().observe(this, Observer<List<Article>> {
            swipeRefreshLayout.isRefreshing = false
            mAdapter.updateList(it)
        })
        viewModel.load()
    }
}
