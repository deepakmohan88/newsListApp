package com.testapp.newslistapp.data

import com.google.gson.annotations.SerializedName

data class NewsDetail(
        @SerializedName("title")
        val title: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("imageHref")
        val imageHref: String
)