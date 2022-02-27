package com.bignerdranch.android.photogallery

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bignerdranch.android.photogallery.api.PhotoResponse

class PhotoPagingSource(
    private val photos:List<GalleryItem>?
):PagingSource<Int, GalleryItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GalleryItem> {
        val pageNumber = params.key ?: 0
        return try{
            val items = photos
            LoadResult.Page(
                data = items!!,
                prevKey = if (pageNumber > 0) pageNumber - 1 else null,
                nextKey = if (items.isNotEmpty()) pageNumber + 1 else null
            )
        } catch (e:Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GalleryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}