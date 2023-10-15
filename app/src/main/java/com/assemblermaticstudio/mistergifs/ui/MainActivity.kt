package com.assemblermaticstudio.mistergifs.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.assemblermaticstudio.mistergifs.R
import com.assemblermaticstudio.mistergifs.databinding.ActivityMainBinding
import com.assemblermaticstudio.mistergifs.ui.fragments.FavGifsFragment
import com.assemblermaticstudio.mistergifs.ui.fragments.HomeFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val homeFragment = HomeFragment();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        // !!don't forget to call addToBackStack and setReorderingAllowed, so latter popBackStack() works
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .setReorderingAllowed(true)
            .add(R.id.fragment_main, homeFragment)
            .commit()
    }
}