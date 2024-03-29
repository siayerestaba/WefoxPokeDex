package com.iliaberlana.wefoxpokedex.ui.commons

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.iliaberlana.wefoxpokedex.R

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
    LayoutInflater.from(context).inflate(layoutRes, this, false)

fun Context.toast(context: Context = applicationContext, message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message, duration).show()
}

fun ImageView.loadImage(path: String?) {
    Glide.with(context)
        .load(path)
        .fitCenter()
        .placeholder(R.mipmap.placeholder)
        .into(this)
}

fun ImageView.cleanImage() {
    Glide.with(context).clear(this)
    setImageResource(R.mipmap.placeholder)
}

fun String.logDebug(message: String) {
    Log.d(this, message)
}