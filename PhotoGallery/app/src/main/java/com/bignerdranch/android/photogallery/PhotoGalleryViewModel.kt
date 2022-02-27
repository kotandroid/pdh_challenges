package com.bignerdranch.android.photogallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow

class PhotoGalleryViewModel:ViewModel() {
    val galleryItemLiveData:LiveData<List<GalleryItem>>

    init {
        galleryItemLiveData = FlickrFetchr().fetchPhotos()
    }

    fun getContent(): Flow<PagingData<GalleryItem>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { PhotoPagingSource(galleryItemLiveData.value)}
        ).flow.cachedIn(viewModelScope)
    }
}