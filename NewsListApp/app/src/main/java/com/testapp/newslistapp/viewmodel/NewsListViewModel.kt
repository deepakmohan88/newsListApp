package com.testapp.newslistapp.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.testapp.newslistapp.AppSchedulers
import com.testapp.newslistapp.data.NewsDetail
import com.testapp.newslistapp.data.Resource
import com.testapp.newslistapp.data.Status
import com.testapp.newslistapp.repository.NewsRepository
import com.testapp.newslistapp.service.NewsResponse
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

/**
 * ViewModel which provides data streams to the ui
 */
class NewsListViewModel(private var newsRepository: NewsRepository, private var schedulers: AppSchedulers) : ViewModel() {

    private val _newsList = MutableLiveData<Resource<List<NewsDetail>>>()
    private val _title = MutableLiveData<String>()
    private val compositeDisposable = CompositeDisposable()
    private var newsListData: List<NewsDetail>? = null

    val isLoading = ObservableBoolean(false)
    val title: LiveData<String>
        get() = _title

    val newsList: LiveData<Resource<List<NewsDetail>>>
        get() = _newsList

    fun loadNewsList() {
        isLoading.set(true)
        _newsList.value = Resource.loading(newsListData)
        compositeDisposable.add(newsRepository.getNews()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.mainThread()).subscribeWith(object : DisposableObserver<Resource<NewsResponse>>() {

                    override fun onError(e: Throwable) {
                        isLoading.set(false)
                        Resource.error(newsListData)
                    }

                    override fun onNext(resource: Resource<NewsResponse>) {
                        resource?.let {
                            it.data?.let {
                                it.title?.let { _title.value = it }
                                it.items?.let {
                                    newsListData = it
                                }
                            }
                            _newsList.value = when (resource.status) {
                                Status.SUCCESS -> Resource.success(newsListData)
                                Status.LOADING -> Resource.loading(newsListData)
                                Status.ERROR -> Resource.error(newsListData)
                            }
                        }
                    }

                    override fun onComplete() {
                        isLoading.set(false)
                    }
                }))
        isLoading.set(false)
    }

}