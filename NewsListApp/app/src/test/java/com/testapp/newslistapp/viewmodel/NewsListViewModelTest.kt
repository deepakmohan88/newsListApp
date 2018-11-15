package com.testapp.newslistapp.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import com.nhaarman.mockito_kotlin.*
import com.testapp.newslistapp.AppSchedulers
import com.testapp.newslistapp.data.NewsDetail
import com.testapp.newslistapp.data.Resource
import com.testapp.newslistapp.repository.NewsRepository
import com.testapp.newslistapp.service.NewsResponse
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito

class NewsListViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val newListRepository = Mockito.mock(NewsRepository::class.java)
    private val testScheduler = TestScheduler()
    private val appSchedulers = AppSchedulers(testScheduler,testScheduler, testScheduler)
    private var repoViewModel = NewsListViewModel(newListRepository, appSchedulers)
    private lateinit var newsResponse: NewsResponse

    @Test
    fun fetchNewsDetailsListSuccessfulWhenObserved() {
        whenever(newListRepository.getNews()).thenReturn(getNewsListObservableWithData())
        var observer = mock<Observer<Resource<List<NewsDetail>>>>()
        repoViewModel.newsList.observeForever(observer)
        repoViewModel.loadNewsList()

        testScheduler.triggerActions()
        Mockito.verify(newListRepository).getNews()

        val argumentCaptor = argumentCaptor<Resource<List<NewsDetail>>>()
        argumentCaptor.run {
            verify(observer, times(2)).onChanged(capture())
            val (loadingState, successState) = allValues
            assertEquals(loadingState, Resource.loading(null))
            assertEquals(successState, Resource.success(newsResponse.items))
        }
    }

    @Test
    fun fetchTitleSuccessfulWhenObserved() {
        whenever(newListRepository.getNews()).thenReturn(getNewsListObservableWithData())
        var observer = mock<Observer<String>>()
        repoViewModel.title.observeForever(observer)
        repoViewModel.loadNewsList()
        testScheduler.triggerActions()

        Mockito.verify(newListRepository).getNews()
        verify(observer).onChanged(ArgumentMatchers.refEq("Page title"))
    }

    @Test
    fun fetchDataErrorWhenObserved() {
        whenever(newListRepository.getNews()).thenReturn(Observable.error(Throwable()))
        var observer = mock<Observer<Resource<List<NewsDetail>>>>()
        repoViewModel.newsList.observeForever(observer)
        repoViewModel.loadNewsList()

        testScheduler.triggerActions()

        Mockito.verify(newListRepository).getNews()
        val argumentCaptor = argumentCaptor<Resource<List<NewsDetail>>>()
        argumentCaptor.run {
            verify(observer, times(2)).onChanged(capture())
            val (loadingState, errorState) = allValues
            assertEquals(loadingState, Resource.loading(null))
            assertEquals(errorState, Resource.error(null))
        }
    }

    @Test
    fun fetchTitleErrorWhenObserved() {
        whenever(newListRepository.getNews()).thenReturn(Observable.error(Throwable()))
        var observer = mock<Observer<String>>()
        repoViewModel.title.observeForever(observer)
        repoViewModel.loadNewsList()
        testScheduler.triggerActions()

        Mockito.verify(newListRepository).getNews()
        verify(observer, never()).onChanged(any())
    }

    private fun getNewsListObservableWithData() : Observable<NewsResponse>{
        var newsDetail1 = NewsDetail("Title1", "Description1", "testUrl1")
        var newsDetail2 = NewsDetail("Title2", "Description2", "testUrl2")
        var newsDetail3 = NewsDetail("Title3", "Description3", "testUrl3")
        newsResponse = NewsResponse("Page title", listOf(newsDetail1, newsDetail2, newsDetail3))
        return Observable.just(newsResponse)
    }

}