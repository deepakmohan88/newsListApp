package com.testapp.newslistapp.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.testapp.newslistapp.databinding.NewsListFragmentBinding
import com.testapp.newslistapp.ui.adapter.NewsRecyclerViewAdapter
import com.testapp.newslistapp.viewmodel.MainViewModelFactory
import com.testapp.newslistapp.viewmodel.NewsListViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class NewsListFragment : DaggerFragment() {

    lateinit var binding: NewsListFragmentBinding
    lateinit var newsListViewModel: NewsListViewModel
    private val recipeListAdapter = NewsRecyclerViewAdapter(arrayListOf())

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = NewsListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        newsListViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(NewsListViewModel::class.java)

        binding.viewModel = newsListViewModel
        binding.newsListRv.layoutManager = LinearLayoutManager(activity)
        binding.newsListRv.adapter = recipeListAdapter
        binding.executePendingBindings()

        initTitle()
        initNewsList()
    }

    private fun initTitle() {
        newsListViewModel.title.observe(this, Observer { it?.let { activity?.setTitle(it) } })
    }

    private fun initNewsList() {
        newsListViewModel.newsList.observe(this, Observer { it?.let { recipeListAdapter.replaceData(it) } })
        newsListViewModel.loadNewsList()
    }
}
