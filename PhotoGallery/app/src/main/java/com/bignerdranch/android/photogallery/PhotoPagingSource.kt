package com.bignerdranch.android.photogallery

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bignerdranch.android.photogallery.api.FlickrApi

class PhotoPagingSource(
    private val flickrApi: FlickrApi,
    private val photoId: String
):PagingSource<Int, GalleryItem>() {

    override fun getRefreshKey(state: PagingState<Int, GalleryItem>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GalleryItem> {
        return try{
            val position = params.key ?: 1
            val response = flickrApi.fetchPhotos()
            val
        } catch (e:Exception) {
            LoadResult.Error(e)
        }
    }
}