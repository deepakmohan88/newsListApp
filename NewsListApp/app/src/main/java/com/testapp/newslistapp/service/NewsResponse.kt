package com.testapp.newslistapp.service

import com.google.gson.annotations.SerializedName
import com.testapp.newslistapp.data.NewsDetail

data class NewsResponse(
        @SerializedName("title")
        val title: String,
        @SerializedName("rows")
        val items: List<NewsDetail>
)