package com.assemblermaticstudio.mistergifs.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.assemblermaticstudio.mistergifs.R
import com.assemblermaticstudio.mistergifs.databinding.ActivityMainBinding
import com.assemblermaticstudio.mistergifs.di.Modules
import com.assemblermaticstudio.mistergifs.ui.fragments.HomeFragment
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val homeFragment = HomeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        startKoin {
            androidContext(applicationContext)
        }
        Modules.load(applicationContext)


        // !!don't forget to call addToBackStack and setReorderingAllowed, so latter popBackStack() works
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .setReorderingAllowed(true)
            .add(R.id.fragment_main, homeFragment)
            .commit()

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val startMain = Intent(Intent.ACTION_MAIN)
                startMain.addCategory(Intent.CATEGORY_HOME)
                startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(startMain)
            }

        })
    }


}