package com.testapp.newslistapp.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.testapp.newslistapp.R
import com.testapp.newslistapp.data.NewsDetail
import com.testapp.newslistapp.data.Resource
import com.testapp.newslistapp.data.Status
import com.testapp.newslistapp.databinding.NewsListFragmentBinding
import com.testapp.newslistapp.ui.adapter.NewsRecyclerViewAdapter
import com.testapp.newslistapp.viewmodel.MainViewModelFactory
import com.testapp.newslistapp.viewmodel.NewsListViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * Fragment which displays the news data
 */
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
        newsListViewModel.newsList.observe(this, Observer { it?.let { showNewsListState(it) } })
        newsListViewModel.loadNewsList()
    }

    private fun showNewsListState(newsDataResource : Resource<List<NewsDetail>>) {
        when(newsDataResource.status) {
            Status.SUCCESS -> newsDataResource?.data?.let { recipeListAdapter.replaceData(it) }
            Status.ERROR -> showErrorToast()
        }
    }

    private fun showErrorToast(){
        Toast.makeText(activity, getString(R.string.error_loading_data), Toast.LENGTH_SHORT).show()
    }
}
