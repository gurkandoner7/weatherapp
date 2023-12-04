package com.portal.weatherapp.utilities.extensions


import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImageWithUrl(url: String?) {
    val baseUrl = "https://openweathermap.org/img/wn/$url.png"
    Glide.with(context)
        .load(baseUrl)
        .apply(RequestOptions().transform(FitCenter()))
        .into(this)
}

fun ImageView.loadImage(drawableRes: Int) {
    Glide.with(context).load(drawableRes).into(this)
}



