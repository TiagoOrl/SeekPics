package com.assemblermaticstudio.mistergifs.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.assemblermaticstudio.mistergifs.R
import com.assemblermaticstudio.mistergifs.model.GIF
import com.bumptech.glide.Glide

class GifListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<GIF> = mutableListOf()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return GifViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.card_item, p0, false))
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        when(p0) {
            is GifViewHolder -> {
                p0.bind(items.get(p1))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(gifList: List<GIF>) {
        items = gifList
    }

    inner class GifViewHolder constructor(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
//        val url_embed = itemView.findViewById<TextView>(R.id.url_embed)
        val title = itemView.findViewById<TextView>(R.id.tv_gif_title)
        val gif_main = itemView.findViewById<ImageView>(R.id.iv_gif)

        fun bind(gif: GIF){
//            url_embed.text = gif.embed_url
            Glide
                .with(itemView.context)
                .load(gif.images.original.url)
                .error(R.drawable.ic_launcher_background)
                .into(gif_main)

            title.text = gif.title

        }
    }

}

