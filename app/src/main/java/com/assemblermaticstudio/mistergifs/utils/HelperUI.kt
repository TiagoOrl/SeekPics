package com.assemblermaticstudio.mistergifs.utils

import android.content.Context
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder

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
    }
}