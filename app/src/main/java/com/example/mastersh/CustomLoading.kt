package com.example.mastersh

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable

object CustomLoading {
    private var dialog: Dialog? = null

    fun showLoading(activity: Activity){
        val dialogView = activity.layoutInflater.inflate(R.layout.layout_loading, null, false)

        dialog = Dialog(activity)
        dialog?.setCancelable(false) // supaya saat sudah loading tidak bisa di cancel
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setContentView(dialogView)

        dialog?.show()
    }

    fun hideLoading(){
        dialog?.dismiss()
    }
}