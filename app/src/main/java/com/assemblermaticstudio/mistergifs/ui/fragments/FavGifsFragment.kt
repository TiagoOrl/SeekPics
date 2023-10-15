package com.assemblermaticstudio.mistergifs.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.assemblermaticstudio.mistergifs.R
import com.assemblermaticstudio.mistergifs.databinding.FragmentFavouriteGifsBinding
import com.assemblermaticstudio.mistergifs.di.Modules
import com.assemblermaticstudio.mistergifs.ui.GifListAdapter
import com.assemblermaticstudio.mistergifs.ui.MainActivity
import com.assemblermaticstudio.mistergifs.utils.HelperUI
import com.assemblermaticstudio.mistergifs.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin

class FavGifsFragment() : Fragment(R.layout.fragment_favourite_gifs) {
    private lateinit var binding: FragmentFavouriteGifsBinding
    private val loadingDialog by lazy { HelperUI.createProgressDialog(context) }
    private val viewModel by viewModel<MainViewModel>()
    private val adapter by lazy { GifListAdapter(viewModel) }

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

//        Modules.load(requireContext())
        initRecyclerView()

        viewModel.output.observe(viewLifecycleOwner) {
            when(it) {
                MainViewModel.State.Loading -> loadingDialog.show()

                is MainViewModel.State.Error -> {
                    HelperUI.createDialog(it.error.message, requireContext()).show()
                    loadingDialog.dismiss()
                }
                is MainViewModel.State.SuccessQuery -> {
                    loadingDialog.dismiss()
                    adapter.submitList(it.list)
                    binding.tvFavCount.text = "Count: ${it.list.size}"
                    adapter.notifyDataSetChanged()
                }
            }
        }

        viewModel.getFavouriteGifs()
        initViews()
    }

    private fun initRecyclerView() {
        binding.rvFavouriteGifs.adapter = adapter
        binding.rvFavouriteGifs.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initViews() {
        binding.chpSearchBtn.setOnClickListener {
            val fragmentManager = requireActivity().supportFragmentManager

            if (fragmentManager.fragments.size > 0)
                fragmentManager.popBackStack()
        }
    }
}