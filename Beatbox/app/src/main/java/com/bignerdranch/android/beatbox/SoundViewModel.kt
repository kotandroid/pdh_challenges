package com.bignerdranch.android.beatbox

import android.util.Log
import android.widget.SeekBar
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData

class SoundViewModel(private val beatBox:BeatBox) : BaseObservable() {

    private var playbackSpeed: Int = 0
    private var playbackSpeedDisplay: Int = 0

    @Bindable
    fun getPlaybackSpeed():Int {
        return playbackSpeed
    }

    fun setPlaybackSpeed(playbackSpeed:Int){
        this.playbackSpeed = playbackSpeed
        notifyChange()
        setPlaybackSpeedDisplay(playbackSpeed)
        changePlaybackSpeed(playbackSpeed.toFloat())
        Log.d("TEST", playbackSpeed.toString())
    }

    @Bindable
    fun getPlaybackSpeedDisplay():String{
        return playbackSpeedDisplay.toString()
    }

    fun setPlaybackSpeedDisplay(playbackSpeed: Int){
        this.playbackSpeedDisplay = playbackSpeed
        notifyChange()
    }

    private fun changePlaybackSpeed(progress: Float){
        beatBox.playbackSpeed = progress
    }

    fun onButtonClicked() {
        sound?.let{
            beatBox.play(it)
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