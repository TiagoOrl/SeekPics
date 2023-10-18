package com.assemblermaticstudio.mistergifs.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.assemblermaticstudio.mistergifs.R
import com.assemblermaticstudio.mistergifs.databinding.ActivityMainBinding
import com.assemblermaticstudio.mistergifs.di.Modules
import com.assemblermaticstudio.mistergifs.ui.fragments.GifsFragment
import com.assemblermaticstudio.mistergifs.ui.fragments.ImagesFragment
import com.assemblermaticstudio.mistergifs.utils.HelperUI
import com.google.android.material.navigation.NavigationView
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private val gifsFragment = GifsFragment()
    private val imagesFragment = ImagesFragment()
    private lateinit var mainDrawer: DrawerLayout
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        toolbar = findViewById(R.id.toolbar)
        mainDrawer = binding.dlMain
        HelperUI.initializeNavDrawer(toolbar, mainDrawer, this, binding)

        startKoin {
            androidContext(applicationContext)
        }
        Modules.load(applicationContext)


        // !!don't forget to call addToBackStack and setReorderingAllowed, so latter popBackStack() works
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .setReorderingAllowed(true)
            .add(R.id.fragment_main, gifsFragment)
            .commit()

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (supportFragmentManager.fragments.size > 1)
                    supportFragmentManager.popBackStack()
                else {
                    val startMain = Intent(Intent.ACTION_MAIN)
                    startMain.addCategory(Intent.CATEGORY_HOME)
                    startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(startMain)
                }
            }

        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val stackSize = supportFragmentManager.fragments.size
        if (stackSize > 1)
            supportFragmentManager.popBackStackImmediate()

        if (item.itemId == R.id.opt_gifs) {
            if (!supportFragmentManager.fragments.contains(gifsFragment)) {
                supportFragmentManager
                    .beginTransaction()
                    .addToBackStack(null)
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_main, gifsFragment)
                    .commit()
            }
        }
        if (item.itemId == R.id.opt_images) {
            if (!supportFragmentManager.fragments.contains(imagesFragment)) {
                supportFragmentManager
                    .beginTransaction()
                    .addToBackStack(null)
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_main, imagesFragment)
                    .commit()
            }
        }
        mainDrawer.closeDrawer(GravityCompat.START)
        return true
    }
}