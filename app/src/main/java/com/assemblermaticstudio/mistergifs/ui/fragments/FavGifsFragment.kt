package com.assemblermaticstudio.mistergifs.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.assemblermaticstudio.mistergifs.R
import com.assemblermaticstudio.mistergifs.databinding.FragmentFavouriteGifsBinding
import com.assemblermaticstudio.mistergifs.ui.GifListAdapter
import com.assemblermaticstudio.mistergifs.utils.HelperUI
import com.assemblermaticstudio.mistergifs.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavGifsFragment : Fragment(R.layout.fragment_favourite_gifs) {
    private lateinit var binding: FragmentFavouriteGifsBinding
    private val loadingDialog by lazy { HelperUI.createProgressDialog(context) }
    private val viewModel by viewModel<MainViewModel>()
    private val adapter by lazy { GifListAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteGifsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}