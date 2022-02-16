package com.bignerdranch.android.photogallery.api

import com.bignerdranch.android.photogallery.GalleryItem
import com.google.gson.annotations.SerializedName

class PhotoResponse {
    @SerializedName("photos")
    lateinit var galleryItems: List<GalleryItem>
}