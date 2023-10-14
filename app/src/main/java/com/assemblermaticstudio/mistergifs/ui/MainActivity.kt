package com.assemblermaticstudio.mistergifs.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.assemblermaticstudio.mistergifs.core.createDialog
import com.assemblermaticstudio.mistergifs.core.createProgressDialog
import com.assemblermaticstudio.mistergifs.databinding.ActivityMainBinding
import com.assemblermaticstudio.mistergifs.di.Modules
import com.assemblermaticstudio.mistergifs.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.startKoin

class MainActivity : AppCompatActivity() {

    private val loadingDialog by lazy { createProgressDialog() }
    private val viewModel by viewModel<MainViewModel>()
    private val adapter by lazy { GifListAdapter() }
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        startKoin {
            androidContext(this@MainActivity)
        }
        Modules.load(applicationContext)
        initRecyclerView()

        viewModel.output.observe(this) {
            when(it) {
                MainViewModel.State.Loading -> loadingDialog.show()
                is MainViewModel.State.Error -> {
                    createDialog {
                        setMessage(it.error.message)
                    }.show()
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

        viewModel.queryGifs()
    }

    private fun initRecyclerView() {
        binding.mainsListRv.adapter = adapter
        binding.mainsListRv.layoutManager = LinearLayoutManager(this@MainActivity)
    }
}