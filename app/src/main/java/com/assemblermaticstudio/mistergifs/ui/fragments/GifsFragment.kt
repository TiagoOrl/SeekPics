package com.assemblermaticstudio.mistergifs.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.assemblermaticstudio.mistergifs.R
import com.assemblermaticstudio.mistergifs.utils.HelperUI
import com.assemblermaticstudio.mistergifs.databinding.FragmentHomeBinding
import com.assemblermaticstudio.mistergifs.ui.adapters.GifListAdapter
import com.assemblermaticstudio.mistergifs.viewmodel.GifsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class GifsFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val loadingDialog by lazy { HelperUI.createProgressDialog(context) }
    private val viewModel by viewModel<GifsViewModel>()
    private val adapter by lazy { GifListAdapter(viewModel) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        viewModel.output.observe(viewLifecycleOwner) {
            when(it) {
                GifsViewModel.State.Loading -> loadingDialog.show()
                is GifsViewModel.State.Error -> {
                    HelperUI.createDialog(it.error.message, requireContext()).show()
                    loadingDialog.dismiss()
                }
                is GifsViewModel.State.Success -> {
                    loadingDialog.dismiss()
                    adapter.submitList(it.dataObject.list)
                    adapter.notifyDataSetChanged()
                }
                is GifsViewModel.State.SuccessQuery -> {
                    loadingDialog.dismiss()
                    adapter.submitList(it.list)
                    adapter.notifyDataSetChanged()
                }
                is GifsViewModel.State.SuccessEmpty -> {
                    loadingDialog.dismiss()
                    viewModel.getTrendingGifs(5)
                }
                is GifsViewModel.State.EmptyFavs -> {
                    loadingDialog.dismiss()
                }
                else -> {}
            }
        }
        initViews()
        viewModel.getAllLocalGifs()
    }

    private fun initRecyclerView() {
        binding.mainsListRv.adapter = adapter
        binding.mainsListRv.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initViews() {
        binding.findGif.setOnClickListener {
            HelperUI.closeKeyboard(requireActivity());

            if (!binding.searchEt.text.toString().equals(""))
                viewModel.searchGif(binding.searchEt.text.toString(), binding.counSelTv.text.toString().toInt())
        }

        binding.trendingBtn.setOnClickListener {
            HelperUI.closeKeyboard(requireActivity());
            if (binding.counSelTv.text.toString().toInt() < 1)
                binding.counSelTv.text = 1.toString()
            viewModel.getTrendingGifs(binding.counSelTv.text.toString().toInt())
        }

        binding.gifsCountSb .max = 22
        binding.gifsCountSb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                HelperUI.closeKeyboard(requireActivity())
                val pos = p1 + 1
                binding.counSelTv.text = pos.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                val pos = p0?.progress?.plus(1)
                binding.counSelTv.text = pos.toString()
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                val pos = p0?.progress?.plus(1)
                binding.counSelTv.text = pos.toString()
            }

        })

        binding.chpFavsBtn.setOnClickListener {
            viewModel.getFavouriteGifs()
        }
    }
}