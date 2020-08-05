package com.balocco.androidcomponents.data.remote

import android.widget.ImageView
import com.squareup.picasso.Picasso
import javax.inject.Inject

class ImageLoader @Inject constructor(
    private val picasso: Picasso
) {

    fun loadImage(target: ImageView, imageName: String) {
        val imageUrl = buildImageUrl(imageName)
        picasso.load(imageUrl).into(target)
    }

    private fun buildImageUrl(imageName: String) =
        "${RemoteDataSource.BASE_IMAGE_URL}${imageName}"
}