package com.example.imperative.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imperative.R
import com.example.imperative.databinding.ItemTvShortBinding
import com.example.imperative.fragment.DetailsFragment

class TVShortAdapter(var activity: DetailsFragment, var items: List<String>) : BaseAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_tv_short, parent, false)
        return TVShortViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tvShort: String = items[position]
        if (holder is TVShortViewHolder) {
            val ivShort = holder.binding.ivMovie

            Glide.with(activity).load(tvShort).into(ivShort)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class TVShortViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemTvShortBinding.bind(view)
    }
}