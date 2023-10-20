package com.assemblermaticstudio.mistergifs.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assemblermaticstudio.mistergifs.R
import com.bumptech.glide.Glide

import com.assemblermaticstudio.mistergifs.databinding.CardImgItemBinding
import com.assemblermaticstudio.mistergifs.model.image.Image
import com.assemblermaticstudio.mistergifs.viewmodel.ImagesViewModel

class ImagesListAdapter(
    private val imagesViewModel: ImagesViewModel
) : RecyclerView.Adapter<ImagesListAdapter.ImageViewHolder>() {

    private var items: ArrayList<Image> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = CardImgItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, index: Int) {
        holder.bind(items[index])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(list: ArrayList<Image>) {
        items = list
    }

    inner class ImageViewHolder(
        private val binding: CardImgItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val ivImage = binding.ivImg
        private val tvTitle = binding.tvImgTitle
        private val tvImgBy = binding.tvImgBy
        private val ivFavBtn = binding.ivFavImgButton
        private val ivShareBtn = binding.ivShareImgBtn

        fun bind(image: Image) {
            Glide
                .with(itemView)
                .load(image.source.tiny)
                .error(R.drawable.ic_launcher_background)
                .into(ivImage)

            tvTitle.text = image.title
            tvImgBy.text = image.by

            if (image.fav)
                binding.ivFavImgButton.setBackgroundResource(R.drawable.fav)
            else
                binding.ivFavImgButton.setBackgroundResource(R.drawable.add_fav)

            ivFavBtn.setOnClickListener {
                imagesViewModel.toggleFavourite(image)
                if (image.fav)
                    ivFavBtn.setBackgroundResource(R.drawable.fav)
                else {
                    items.remove(image)
                    ivFavBtn.setBackgroundResource(R.drawable.add_fav)
                }
                notifyDataSetChanged()
            }

            ivShareBtn.setOnClickListener {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, image.source.original)
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                binding.root.context.startActivity(shareIntent)
            }
        }
    }
}