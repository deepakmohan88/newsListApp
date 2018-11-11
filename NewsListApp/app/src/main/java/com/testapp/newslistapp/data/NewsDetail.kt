package com.testapp.newslistapp.data

import java.io.Serializable

data class NewsDetail(
        val title: String,
        val description: String,
        val imageUrl: String
):Serializable