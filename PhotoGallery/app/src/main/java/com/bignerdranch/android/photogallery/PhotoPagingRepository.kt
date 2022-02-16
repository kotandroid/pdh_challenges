package com.bignerdranch.android.photogallery

import com.bignerdranch.android.photogallery.api.FlickrApi
import kotlinx.coroutines.flow.Flow

class PhotoPagingRepository(private val flickerApi:FlickrApi) {

    fun getPhotoItemsByPaging(): Flow<>

}