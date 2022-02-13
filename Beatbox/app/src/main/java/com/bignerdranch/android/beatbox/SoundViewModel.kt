package com.bignerdranch.android.beatbox

import android.util.Log
import android.widget.SeekBar
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData

class SoundViewModel(private val beatBox:BeatBox) : BaseObservable() {

    val playBackSpeed = MutableLiveData(0)

    fun onSeekbarChanged(progress:Int){
        playBackSpeed.value = progress
        Log.d("TEST", progress.toString())
    }

    fun onButtonClicked() {
        sound?.let{
            beatBox.play(it, (playBackSpeed.value?.toFloat()!!.div(100)))
        }
    }

    var sound:Sound? = null
        set(sound) {
            field = sound
            notifyChange()
        }

    @get:Bindable
    val title: String?
        get() = sound?.name

}