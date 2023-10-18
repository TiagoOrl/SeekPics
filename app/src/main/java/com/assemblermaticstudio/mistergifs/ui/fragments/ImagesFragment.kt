package com.assemblermaticstudio.mistergifs.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.assemblermaticstudio.mistergifs.R
import com.assemblermaticstudio.mistergifs.databinding.FragmentImagesBinding
import com.assemblermaticstudio.mistergifs.ui.adapters.ImagesListAdapter
import com.assemblermaticstudio.mistergifs.utils.HelperUI
import com.assemblermaticstudio.mistergifs.utils.Helpers
import com.assemblermaticstudio.mistergifs.viewmodel.ImagesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale

class ImagesFragment : Fragment(R.layout.fragment_images) {
    private lateinit var binding: FragmentImagesBinding
    private val loadingDialog by lazy { HelperUI.createProgressDialog(context) }
    private val viewModel by viewModel<ImagesViewModel>()
    private val adapter by lazy { ImagesListAdapter(viewModel) }
    private var orientation: String = "any"
    private var imagesCount = 10

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImagesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        viewModel.output.observe(viewLifecycleOwner) {
            when(it) {
                ImagesViewModel.State.Loading -> loadingDialog.show()
                is ImagesViewModel.State.Error -> {
                    HelperUI.createDialog(it.error.message, requireContext()).show()
                    loadingDialog.dismiss()
                }
                is ImagesViewModel.State.SuccessGet -> {
                    loadingDialog.dismiss()
                    adapter.submitList(it.dataObject.list)
                    adapter.notifyDataSetChanged()
                }
                is ImagesViewModel.State.SuccessQuery -> {
                    loadingDialog.dismiss()
                    adapter.submitList(it.list)
                    adapter.notifyDataSetChanged()
                }
                is ImagesViewModel.State.SuccessEmpty -> {
                    loadingDialog.dismiss()
                    viewModel.getCuratedImages(15)
                }
                is ImagesViewModel.State.EmptyFavs -> {
                    loadingDialog.dismiss()
                    adapter.notifyDataSetChanged()
                }
                else -> {}
            }
        }

        initViews()
        viewModel.getAllLocal()
    }

    private fun initRecyclerView() {
        binding.rvImages.adapter = adapter
        binding.rvImages.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun initViews() {
        binding.sbLimit.max = 50
        binding.sbLimit.progress = 10
        binding.sbLimit.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, pos: Int, p2: Boolean) {
                HelperUI.closeKeyboard(requireActivity())
                imagesCount = pos + 1
                binding.tvCount.text = imagesCount.toString()
            }

            override fun onStartTrackingTouch(seekbar: SeekBar?) {
                if (seekbar != null) {
                    imagesCount = seekbar.progress.plus(1)
                    binding.tvCount.text = imagesCount.toString()
                }
            }

            override fun onStopTrackingTouch(seekbar: SeekBar?) {
                if (seekbar != null) {
                    imagesCount = seekbar.progress.plus(1)
                    binding.tvCount.text = imagesCount.toString()
                }
            }
        })

        binding.chkLandscape.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                orientation = "landscape"
                binding.chkPortrait.isChecked = false
                binding.chkSquare.isChecked = false
                binding.chkAny.isChecked = false
            }
        }

        binding.chkPortrait.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                orientation = "portrait"
                binding.chkLandscape.isChecked = false
                binding.chkSquare.isChecked = false
                binding.chkAny.isChecked = false
            }
        }

        binding.chkSquare.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                orientation = "square"
                binding.chkLandscape.isChecked = false
                binding.chkPortrait.isChecked = false
                binding.chkAny.isChecked = false
            }
        }

        binding.chkAny.isChecked = true
        binding.chkAny.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                orientation = "any"
                binding.chkSquare.isChecked = false
                binding.chkLandscape.isChecked = false
                binding.chkPortrait.isChecked = false
            }
        }

        binding.chpFind.setOnClickListener {
            HelperUI.closeKeyboard(requireActivity())

            if (binding.etSearchImg.text.toString() != "")
                viewModel.searchImages(
                    binding.etSearchImg.text.toString(),
                    orientation,
                    1,
                    imagesCount,
                    Helpers.getLocale())
        }

        binding.chpCurated.setOnClickListener {
            viewModel.getCuratedImages(imagesCount)
        }

        binding.chpFavImgsBtn.setOnClickListener {
            HelperUI.closeKeyboard(requireActivity())
            viewModel.getFavourites()
        }
    }
}