package com.testapp.newslistapp.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.testapp.newslistapp.data.NewsDetail
import com.testapp.newslistapp.repository.NewsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class NewsListViewModel(private var newsRepository: NewsRepository) : ViewModel() {

    private val _newsList = MutableLiveData<List<NewsDetail>>()
    private val compositeDisposable = CompositeDisposable()

    val isLoading = ObservableBoolean(false)

    val newsList: LiveData<List<NewsDetail>>
        get() = _newsList


    fun loadNewsList() {
        isLoading.set(true)
        compositeDisposable.add(newsRepository.getNews()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(object : DisposableObserver<List<NewsDetail>>() {

                    override fun onError(e: Throwable) {
                        //if some error happens in our data layer our app will not crash
                    }

                    override fun onNext(data: List<NewsDetail>) {
                        _newsList.value = data
                    }

                    override fun onComplete() {
                        isLoading.set(false)
                    }
                }))
        isLoading.set(false)
    }

}