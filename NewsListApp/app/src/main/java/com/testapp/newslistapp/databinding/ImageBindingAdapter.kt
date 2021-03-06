package com.testapp.newslistapp.databinding

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Data Binding adapters specific to the app
 */
object ImageBindingAdapter {

    @JvmStatic
    @BindingAdapter("app:imageUrl")
    fun setImageUrl(view: ImageView, url: String?) {
        url?.let { Glide.with(view.context).load(url).into(view) }
    }
}