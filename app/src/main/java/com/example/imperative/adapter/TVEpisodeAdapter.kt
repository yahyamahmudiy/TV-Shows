package com.example.imperative.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.imperative.R
import com.example.imperative.databinding.ItemTvEpisodeBinding
import com.example.imperative.databinding.ItemTvShortBinding
import com.example.imperative.fragment.DetailsFragment
import com.example.imperative.model.Episode

class TVEpisodeAdapter(var activity: DetailsFragment, var items: List<Episode>) : BaseAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_tv_episode, parent, false)
        return TVShortViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val episode: Episode = items[position]
        if (holder is TVShortViewHolder) {
            val tvName = holder.binding.tvName
            val tvEpisode = holder.binding.tvEpisode
            val tvSeason = holder.binding.tvSeason

            tvName.text = episode.name
            tvEpisode.text = episode.episode.toString()
            tvSeason.text = episode.season.toString()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class TVShortViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemTvEpisodeBinding.bind(view)
    }
}