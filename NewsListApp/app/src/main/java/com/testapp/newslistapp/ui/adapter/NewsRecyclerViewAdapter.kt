package com.testapp.newslistapp.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.testapp.newslistapp.data.NewsDetail
import com.testapp.newslistapp.databinding.NewsListItemBinding
import java.util.*

class NewsRecyclerViewAdapter(private var items: ArrayList<NewsDetail>)
    : RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val binding = NewsListItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    fun replaceData(newItems: List<NewsDetail>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    class ViewHolder(private var binding: NewsListItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(newsDetail: NewsDetail) {
            binding.newsDetail = newsDetail
            binding.executePendingBindings()
        }
    }

}