package com.bignerdranch.android.photogallery

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView

class PhotoPageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_page)

        val fm = supportFragmentManager
        val currentFragment = fm.findFragmentById(R.id.fragment_container)

        if (currentFragment == null) {
            val fragment = PhotoPageFragment.newInstance(intent.data!!)
            fm.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }

    override fun onBackPressed() {
        val fm = supportFragmentManager
        val currentFragment = fm.findFragmentById(R.id.fragment_container) as PhotoPageFragment
        val webView = currentFragment.requireView() as WebView
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    companion object {
        fun newIntent(context: Context, photoPageUri: Uri):Intent {
            return Intent(context, PhotoPageActivity::class.java).apply {
                data = photoPageUri
            }
        }
    }
}