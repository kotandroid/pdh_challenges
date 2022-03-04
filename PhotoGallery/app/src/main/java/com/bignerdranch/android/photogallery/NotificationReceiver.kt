package com.bignerdranch.android.photogallery

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

private const val TAG = "NotificationReceiver"

class NotificationReceiver:BroadcastReceiver() {

    override fun onReceive(p0: Context, p1: Intent) {
        Log.i(TAG, "received broadcast: ${p1.action}")
    }
}