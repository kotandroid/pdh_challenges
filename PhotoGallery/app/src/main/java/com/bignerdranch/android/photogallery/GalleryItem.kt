package com.bignerdranch.android.photogallery

import com.google.gson.annotations.SerializedName

data class GalleryItem(
    var title: String = "",
    var id: String = "",
    var url: String = ""
)
