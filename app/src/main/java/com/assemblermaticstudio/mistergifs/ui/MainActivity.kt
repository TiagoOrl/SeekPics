package com.assemblermaticstudio.mistergifs.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.assemblermaticstudio.mistergifs.R
import com.assemblermaticstudio.mistergifs.databinding.ActivityMainBinding
import com.assemblermaticstudio.mistergifs.ui.fragments.HomeFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val homeFragment = HomeFragment();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportFragmentManager
            .beginTransaction()
            .setReorderingAllowed(true)
            .add(R.id.fragment_main, homeFragment)
            .commit()
    }
}