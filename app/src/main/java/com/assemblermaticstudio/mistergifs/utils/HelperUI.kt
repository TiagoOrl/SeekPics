package com.assemblermaticstudio.mistergifs.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.ProgressBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.assemblermaticstudio.mistergifs.R
import com.assemblermaticstudio.mistergifs.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView

class HelperUI {
    companion object {
        fun createDialog(msg: String?, context: Context?): AlertDialog {
            val builder = MaterialAlertDialogBuilder(context!!)
            builder
                .setPositiveButton(android.R.string.ok, null)
                .setMessage(msg)

            return builder.create()
        }

        fun createProgressDialog(context: Context?): AlertDialog {
            val builder = MaterialAlertDialogBuilder(context!!)
            val padding = 16
            val progressBar = ProgressBar(context)
            progressBar.setPadding(padding, padding, padding, padding)
            builder
                .setPositiveButton(null, null)
                .setView(progressBar)
                .setCancelable(false)


            return builder.create()
        }

        fun closeKeyboard(activity: Activity) {
            val view = activity.currentFocus;

            if (view != null) {
                val manager: InputMethodManager =
                    activity.getSystemService(
                        Context.INPUT_METHOD_SERVICE
                    ) as InputMethodManager
                manager.hideSoftInputFromWindow(
                    view.windowToken, 0
                )
            }
        }

        /**
         * Creates and sets up Drawer Navigation for the given activity
         *
         */
        fun initializeNavDrawer(toolbar: Toolbar, drawerLayout: DrawerLayout, activity: AppCompatActivity, binding: ActivityMainBinding) {
            activity.setSupportActionBar(toolbar)

            val drawerToggle = ActionBarDrawerToggle(
                activity,
                drawerLayout,
                toolbar,
                R.string.nav_open,
                R.string.nav_close
            )

            drawerLayout.addDrawerListener(drawerToggle)
            drawerToggle.syncState()
            binding.nvMain.setNavigationItemSelectedListener(activity as NavigationView.OnNavigationItemSelectedListener)
        }
    }
}