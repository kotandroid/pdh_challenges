package com.bignerdranch.android.sunset

import android.animation.*
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.widget.ImageView
import androidx.core.content.ContextCompat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var isRising:Boolean = true
    private lateinit var sceneView:View
    private lateinit var sunView:View
    private lateinit var skyView:View
    private lateinit var burningAnimator: ValueAnimator
    private lateinit var animatorSet:AnimatorSet

    private val blueSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.blue_sky)
    }

    private val sunsetSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.sunset_sky)
    }

    private val nightSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.night_sky)
    }

    private val brightSunColor: Int by lazy {
        ContextCompat.getColor(this, R.color.bright_sun)
    }

    private val burningSunColor: Int by lazy {
        ContextCompat.getColor(this, R.color.burning_sun)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sceneView = findViewById(R.id.scene)
        sunView = findViewById(R.id.sun)
        skyView = findViewById(R.id.sky)
        burningAnimator = generateBurning()
        animatorSet = AnimatorSet()

        burningAnimator.start()
        sceneView.setOnClickListener {
            burningAnimator.pause()
            startAnimation()
        }
    }

    private fun generateBurning():ValueAnimator{
        val burningAnimator = ValueAnimator
            .ofInt(brightSunColor, burningSunColor)
            .setDuration(500)
        burningAnimator.repeatCount = ValueAnimator.INFINITE
        burningAnimator.repeatMode = ValueAnimator.REVERSE
        burningAnimator.setEvaluator(ArgbEvaluator())
        burningAnimator.addUpdateListener {
            val background = (sunView as ImageView).drawable as GradientDrawable
            background.setColor(it.animatedValue as Int)
        }
        return burningAnimator
    }

    private fun startAnimation(){
        val sunYstart = sunView.top.toFloat()
        val sunYEnd = skyView.height.toFloat()

        val heightAnimator = ObjectAnimator
            .ofFloat(sunView, "y", sunYstart, sunYEnd)
            .setDuration(1500)

        heightAnimator.interpolator = AccelerateInterpolator()

        val sunsetSkyAnimator = ObjectAnimator
            .ofInt(skyView, "backgroundColor", blueSkyColor, sunsetSkyColor)
            .setDuration(1500)
        sunsetSkyAnimator.setEvaluator(ArgbEvaluator())

        val nightSkyAnimator = ObjectAnimator
            .ofInt(skyView, "backgroundColor", sunsetSkyColor, nightSkyColor)
            .setDuration(750)
        nightSkyAnimator.setEvaluator(ArgbEvaluator())

        animatorSet.play(heightAnimator)
            .with(sunsetSkyAnimator) // 같이 작동하는 애니메이션
            .before(nightSkyAnimator) // 이 애니메이션 전에 작동하기

        if (isRising){
            animatorSet.start()
            isRising = false
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val playtime = animatorSet.currentPlayTime
                animatorSet.pause()
                animatorSet.reverse()
                animatorSet.currentPlayTime = animatorSet.totalDuration - playtime

            }
            burningAnimator.start()
            isRising = true
        }
    }
}