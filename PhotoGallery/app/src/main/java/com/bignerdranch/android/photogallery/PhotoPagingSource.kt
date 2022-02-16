package com.bignerdranch.android.photogallery

import androidx.paging.PagingSource
import androidx.paging.PagingState

class PhotoPagingSource:PagingSource<Int, String>() {

    override fun getRefreshKey(state: PagingState<Int, String>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, String> {
        TODO("Not yet implemented")
    }
}