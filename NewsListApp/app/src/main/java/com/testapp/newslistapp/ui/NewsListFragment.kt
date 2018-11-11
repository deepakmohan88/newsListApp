package com.testapp.newslistapp.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.testapp.newslistapp.databinding.NewsListFragmentBinding
import com.testapp.newslistapp.ui.adapter.NewsRecyclerViewAdapter
import com.testapp.newslistapp.viewmodel.NewsListViewModel

class NewsListFragment : Fragment() {

    lateinit var binding: NewsListFragmentBinding

    lateinit var newsListViewModel: NewsListViewModel

    private val recipeListAdapter = NewsRecyclerViewAdapter(arrayListOf())

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = NewsListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        newsListViewModel = ViewModelProviders.of(this)
                .get(NewsListViewModel::class.java)

        binding.viewModel = newsListViewModel
        binding.newsListRv.layoutManager = LinearLayoutManager(activity)
        binding.newsListRv.adapter = recipeListAdapter
        binding.executePendingBindings()
        initNewsList()
    }

    private fun initNewsList() {
        newsListViewModel.newsList.observe(this, Observer { it?.let { recipeListAdapter.replaceData(it) } })
        newsListViewModel.loadNewsList()
    }
}
