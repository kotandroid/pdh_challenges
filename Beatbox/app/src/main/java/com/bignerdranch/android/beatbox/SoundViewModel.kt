package com.bignerdranch.android.beatbox

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class SoundViewModel(private val beatBox:BeatBox) : BaseObservable() {

    fun onSeekbarChanged(progress:Int){
        playBackSpeed = progress.toFloat() / 100
        sound?.let {
            beatBox.playWithRate(it, playBackSpeed)
        }
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

    @get:Bindable
    var playBackSpeed: Float = 1.0f
        set(speed) {
            field = speed
            notifyChange()
        }
}