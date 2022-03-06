package com.bignerdranch.android.photogallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "PhotoGalleryFragment"

class PhotoGalleryFragment:Fragment() {
    private lateinit var photoGalleryViewModel: PhotoGalleryViewModel
    private lateinit var photoRecyclerView: RecyclerView
    private lateinit var pagingAdapter: PagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        photoGalleryViewModel = ViewModelProvider(this).get(PhotoGalleryViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_photo_gallery, container, false)
        pagingAdapter = PagingAdapter()

        photoRecyclerView = view.findViewById(R.id.photo_recycler_view)
        photoRecyclerView.layoutManager = GridLayoutManager(context, getString(R.string.col_num).toInt())
        photoRecyclerView.adapter = pagingAdapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        photoGalleryViewModel.galleryItemLiveData.observe(
//            viewLifecycleOwner,
//            Observer {
//
//            }
//        )

        lifecycleScope.launch {
            photoGalleryViewModel.listData.collect {
                pagingAdapter.submitData(it)
            }
        }
    }

    private class PhotoHolder(itemTextView: TextView):RecyclerView.ViewHolder(itemTextView){
        val bindTitle: (CharSequence) -> Unit = itemTextView::setText
    }

    private class PhotoAdapter(private val galleryItems: List<GalleryItem>):RecyclerView.Adapter<PhotoHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
            val textView = TextView(parent.context)
            return PhotoHolder(textView)
        }

        override fun getItemCount(): Int = galleryItems.size

        override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
            val galleryItem = galleryItems[position]
            holder.bindTitle(galleryItem.title)
        }
    }

    private class PagingAdapter:PagingDataAdapter<GalleryItem, PagingHolder>(PhotoDiffUitl()){

        override fun onBindViewHolder(holder: PagingHolder, position: Int) {
            getItem(position)?.let { holder.bindTitle(it.title) }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingHolder {
            val textView = TextView(parent.context)
            return PagingHolder(textView)
        }

        private class PhotoDiffUitl: DiffUtil.ItemCallback<GalleryItem>(){
            override fun areItemsTheSame(
                oldItem: GalleryItem,
                newItem: GalleryItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: GalleryItem,
                newItem: GalleryItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    private class PagingHolder(itemTextView:TextView):RecyclerView.ViewHolder(itemTextView){
        val bindTitle: (CharSequence) -> Unit = itemTextView::setText
    }

    companion object {
        fun newInstance() = PhotoGalleryFragment()
    }
}