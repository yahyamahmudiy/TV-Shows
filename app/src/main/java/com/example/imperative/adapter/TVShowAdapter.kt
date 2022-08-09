package com.example.imperative.adapter

import android.content.ClipData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imperative.R
import com.example.imperative.activity.MainActivity
import com.example.imperative.databinding.ItemTvShowBinding
import com.example.imperative.fragment.HomeFragment
import com.example.imperative.model.TVShow

class TVShowAdapter(var activity: HomeFragment, var items: ArrayList<TVShow>) : BaseAdapter() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_tv_show, parent, false)
        return TVShowViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val tvShow: TVShow = items[position]
        if (holder is TVShowViewHolder) {
            val ivMovie = holder.binding.ivMovie
            val tvName = holder.binding.tvName
            val tvType = holder.binding.tvType

            Glide.with(activity).load(tvShow.image_thumbnail_path).into(ivMovie)
            tvName.text = tvShow.name
            tvType.text = tvShow.network

            ViewCompat.setTransitionName(ivMovie,tvShow.name)

            //Click the TV Show
            ivMovie.setOnClickListener {
                //save TV Show into Room
                activity.viewModel.insertTVShowsToDB(tvShow)
                //Call Details Activity
                activity.callDetailsFragment(tvShow,ivMovie)
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setNewTVShows(tvShows: ArrayList<TVShow>) {
        items.addAll(tvShows)
        notifyDataSetChanged()
    }

    inner class TVShowViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemTvShowBinding.bind(view)
        /*var iv_movie: ImageView
        var tv_name: TextView
        var tv_type: TextView

        init {
            iv_movie = view.findViewById(R.id.iv_movie)
            tv_name = view.findViewById(R.id.tv_name)
            tv_type = view.findViewById(R.id.tv_type)
        }*/
    }
}