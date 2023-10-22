package com.assemblermaticstudio.mistergifs.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.assemblermaticstudio.mistergifs.R
import com.assemblermaticstudio.mistergifs.databinding.FragmentVisualizeImageBinding
import com.assemblermaticstudio.mistergifs.model.image.Image
import com.assemblermaticstudio.mistergifs.utils.HelperUI.Companion.shareLink
import com.bumptech.glide.Glide

class ImageVisualizeFragment : Fragment(R.layout.fragment_visualize_image) {
    private lateinit var binding: FragmentVisualizeImageBinding
    private lateinit var image: Image

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVisualizeImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {

        Glide
            .with(requireContext())
            .load(image.source.large2x)
            .error(R.drawable.ic_launcher_background)
            .into(binding.ivMainImg)

        binding.tvImgTitle.text = image.title
        binding.tvImgAuthor.text = "by: ${image.by}"

        if (image.fav)
            binding.ivFavImgButton.setBackgroundResource(R.drawable.fav)
        else
            binding.ivFavImgButton.setBackgroundResource(R.drawable.add_fav)

        binding.ivShareImgBtn.setOnClickListener {
            shareLink(image.source.original, binding.root.context)
        }
    }

    fun setImageData(image: Image) {
        this.image = image
    }
}