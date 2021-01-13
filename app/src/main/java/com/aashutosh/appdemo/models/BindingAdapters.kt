package com.aashutosh.appdemo.models

import android.app.Activity
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.ahmadrosid.svgloader.SvgLoader

object BindingAdapters {
    @BindingAdapter("app:imageThumb")
    @JvmStatic
    fun loadImage(imageView: ImageView, imageUrl: String) {
        SvgLoader.pluck()
            .with(imageView.context as Activity?)
            .setPlaceHolder(
                com.aashutosh.appdemo.R.mipmap.ic_launcher,
                com.aashutosh.appdemo.R.mipmap.ic_launcher
            )
            .load(imageUrl, imageView)
    }
}