package com.example.imperative.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imperative.databinding.ItemTvShowBinding
import com.example.imperative.model.TVShow
import com.example.imperative.utils.RandomColor

class SavedAdapter : ListAdapter<TVShow, SavedAdapter.TVShowViewHolder>(Comparator()) {

    //var onItemClick: ((TVShow, ImageView) -> Unit)? = null

    class Comparator : DiffUtil.ItemCallback<TVShow>() {
        override fun areItemsTheSame(oldItem: TVShow, newItem: TVShow): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TVShow, newItem: TVShow): Boolean {
            return oldItem == newItem
        }

    }

    override fun onBindViewHolder(holder: TVShowViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowViewHolder {
        return TVShowViewHolder(ItemTvShowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))
    }

    inner class TVShowViewHolder(private val binding: ItemTvShowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TVShow?) {
            binding.apply {
                tvName.text = item?.name
                tvType.text = item?.network
                Glide.with(ivMovie).load(item?.image_thumbnail_path)
                    .placeholder(RandomColor.randomColor()).into(ivMovie)

                ViewCompat.setTransitionName(ivMovie, item?.name)
                /*root.setOnClickListener {
                    onItemClick?.invoke(item!!, ivMovie)
                }*/
            }
        }


    }
}