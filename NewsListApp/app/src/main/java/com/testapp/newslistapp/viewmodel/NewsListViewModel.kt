package com.testapp.newslistapp.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.testapp.newslistapp.AppSchedulers
import com.testapp.newslistapp.data.NewsDetail
import com.testapp.newslistapp.data.Resource
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

    val isLoading = ObservableBoolean(false)
    val title: LiveData<String>
        get() = _title

    val newsList: LiveData<Resource<List<NewsDetail>>>
        get() = _newsList

    fun loadNewsList() {
        isLoading.set(true)
        _newsList.value = Resource.loading(null)
        compositeDisposable.add(newsRepository.getNews()
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.mainThread()).subscribeWith(object : DisposableObserver<NewsResponse>() {

                    override fun onError(e: Throwable) {
                        isLoading.set(false)
                        _newsList.value = Resource.error(null)
                    }

                    override fun onNext(data: NewsResponse) {
                        data?.let {
                            _title.value = it.title
                            it.items?.let { _newsList.value = Resource.success(it) }
                        }
                    }

                    override fun onComplete() {
                        isLoading.set(false)
                    }
                }))
        isLoading.set(false)
    }

}