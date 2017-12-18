package com.github.jaccek.githubinspector.databinding

import android.databinding.BindingAdapter
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.github.jaccek.githubinspector.R

@BindingAdapter("srcUrl")
fun setImageUrl(imageView: ImageView, imageUrl: String) {
    Glide.with(imageView.context)
            .load(imageUrl)
            .centerCrop()
            .placeholder(R.color.gray)
            .error(R.color.gray)
            .into(imageView)
}

@BindingAdapter("visible")
fun setIsVisible(view: View, isVisible: Boolean) {
    if (isVisible) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}
