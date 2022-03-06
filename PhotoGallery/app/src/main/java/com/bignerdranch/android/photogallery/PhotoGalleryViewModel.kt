package com.bignerdranch.android.photogallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import kotlinx.coroutines.flow.Flow

class PhotoGalleryViewModel:ViewModel() {
//    val galleryItemLiveData:LiveData<List<GalleryItem>> = FlickrFetchr().fetchPhotos()

    val listData =  Pager(
            config = PagingConfig(pageSize = 5),
            pagingSourceFactory = { PhotoPagingSource(FlickrFetchr())}
        ).flow.cachedIn(viewModelScope)
}