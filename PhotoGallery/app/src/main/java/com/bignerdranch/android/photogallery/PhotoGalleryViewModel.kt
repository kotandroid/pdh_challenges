package com.bignerdranch.android.photogallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import kotlinx.coroutines.flow.Flow

class PhotoGalleryViewModel:ViewModel() {
    val galleryItemLiveData:LiveData<List<GalleryItem>> = FlickrFetchr().fetchPhotos()

    fun getContent(): Flow<PagingData<GalleryItem>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { PhotoPagingSource(this)}
        ).flow.cachedIn(viewModelScope)
    }
}