package com.assemblermaticstudio.mistergifs.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.assemblermaticstudio.mistergifs.R
import com.assemblermaticstudio.mistergifs.databinding.CardGifItemBinding
import com.assemblermaticstudio.mistergifs.model.GIF
import com.assemblermaticstudio.mistergifs.viewmodel.MainViewModel
import com.bumptech.glide.Glide

class GifListAdapter(
    private val mainViewModel: MainViewModel
) : RecyclerView.Adapter<GifListAdapter.GifViewHolder>() {

    private var items: List<GIF> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): GifListAdapter.GifViewHolder {
        val binding: CardGifItemBinding = CardGifItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GifViewHolder(binding, mainViewModel)
    }

    override fun onBindViewHolder(p0: GifListAdapter.GifViewHolder, index: Int) {
        p0.bind(items[index])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(gifList: List<GIF>) {
        items = gifList
    }

    inner class GifViewHolder (
        binding: CardGifItemBinding,
        mainViewModel: MainViewModel
    ) : RecyclerView.ViewHolder(binding.root) {
        private val tvTitle: TextView = binding.tvGifTitle
        private val ivGif: ImageView = binding.ivGif
        private val ivFavBtn: ImageView = binding.ivFavButton
        private val ivShareBtn: ImageView = binding.ivShareBtn

        fun bind(gif: GIF){
            Glide
                .with(itemView.context)
                .load(gif.images.original.url)
                .error(R.drawable.ic_launcher_background)
                .into(ivGif)

            tvTitle.text = gif.title

            ivFavBtn.setOnClickListener {
                mainViewModel.setAsFavourite(gif)
            }

        }
    }
}

