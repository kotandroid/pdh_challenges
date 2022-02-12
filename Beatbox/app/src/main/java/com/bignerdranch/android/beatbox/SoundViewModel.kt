package com.bignerdranch.android.beatbox

import android.util.Log
import android.widget.SeekBar
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class SoundViewModel(private val beatBox:BeatBox) : BaseObservable() {

    fun onSeekbarChanged(seekBar:SeekBar, progress:Int, fromUser:Boolean){
        playBackSpeedInt = progress
        playBackSpeed = progress.toFloat() / 100
        Log.d("TEST", playBackSpeedInt.toString())
    }

    fun onButtonClicked() {
        sound?.let{
            beatBox.play(it, playBackSpeed)
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

    @get:Bindable
    var playBackSpeed: Float = 1.0f
        set(speed) {
            field = speed
            notifyChange()
        }

    @get:Bindable
    var playBackSpeedInt: Int = 0
}