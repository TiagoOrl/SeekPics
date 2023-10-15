package com.assemblermaticstudio.mistergifs.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.assemblermaticstudio.mistergifs.R
import com.assemblermaticstudio.mistergifs.utils.HelperUI.Companion.createDialog
import com.assemblermaticstudio.mistergifs.utils.HelperUI.Companion.createProgressDialog
import com.assemblermaticstudio.mistergifs.databinding.FragmentHomeBinding
import com.assemblermaticstudio.mistergifs.di.Modules
import com.assemblermaticstudio.mistergifs.ui.GifListAdapter
import com.assemblermaticstudio.mistergifs.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val loadingDialog by lazy { createProgressDialog(context) }
    private val viewModel by viewModel<MainViewModel>()
    private val adapter by lazy { GifListAdapter(viewModel) }
    private val favGifsFragment: FavGifsFragment = FavGifsFragment()

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

        startKoin {
            androidContext(requireContext())
        }
        Modules.load(requireContext())
        initRecyclerView()

        viewModel.output.observe(viewLifecycleOwner) {
            when(it) {
                MainViewModel.State.Loading -> loadingDialog.show()
                is MainViewModel.State.Error -> {
                    createDialog(it.error.message, requireContext()).show()
                    loadingDialog.dismiss()
                }
                is MainViewModel.State.Success -> {
                    loadingDialog.dismiss()
                    adapter.submitList(it.dataObject.data)
                    adapter.notifyDataSetChanged()
                }
                is MainViewModel.State.SuccessQueryDB -> {
                    loadingDialog.dismiss()
                    adapter.submitList(it.dataObject)
                    adapter.notifyDataSetChanged()
                }
            }
        }
        initViews()
        viewModel.queryGifs()
    }

    private fun initRecyclerView() {
        binding.mainsListRv.adapter = adapter
        binding.mainsListRv.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initViews() {
        binding.findGif.setOnClickListener {
            if (binding.counSelTv.text.toString().toInt() < 1)
                binding.counSelTv.text = 1.toString()
            if (!binding.searchEt.text.toString().equals(""))
                viewModel.searchGif(binding.searchEt.text.toString(), binding.counSelTv.text.toString().toInt())
        }

        binding.trendingBtn.setOnClickListener {
            if (binding.counSelTv.text.toString().toInt() < 1)
                binding.counSelTv.text = 1.toString()
            viewModel.getTrendingGifs(binding.counSelTv.text.toString().toInt())
        }

        binding.gifsCountSb.max = 22
        binding.gifsCountSb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
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
            val fragmentManager = requireActivity().supportFragmentManager

            if (!fragmentManager.fragments.contains(favGifsFragment))
                fragmentManager
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_main, favGifsFragment)
                    .commit()
        }
    }
}