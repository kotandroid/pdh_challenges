package com.bignerdranch.android.sunset

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private var shouldRise:Boolean = false
    private lateinit var sceneView:View
    private lateinit var sunView:View
    private lateinit var skyView:View

    private val blueSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.blue_sky)
    }

    private val sunsetSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.sunset_sky)
    }

    private val nightSkyColor: Int by lazy {
        ContextCompat.getColor(this, R.color.night_sky)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sceneView = findViewById(R.id.scene)
        sunView = findViewById(R.id.sun)
        skyView = findViewById(R.id.sky)

        sceneView.setOnClickListener {
            startAnimation()
        }
    }

    private fun startAnimation(){
        val sunYstart = sunView.top.toFloat()
        val sunYEnd = skyView.height.toFloat()

        if (!shouldRise){
            val heightAnimator = ObjectAnimator
                .ofFloat(sunView, "y", sunYstart, sunYEnd)
                .setDuration(3000)

            heightAnimator.interpolator = AccelerateInterpolator()

            val sunsetSkyAnimator = ObjectAnimator
                .ofInt(skyView, "backgroundColor", blueSkyColor, sunsetSkyColor)
                .setDuration(3000)
            sunsetSkyAnimator.setEvaluator(ArgbEvaluator())

            val nightSkyAnimator = ObjectAnimator
                .ofInt(skyView, "backgroundColor", sunsetSkyColor, nightSkyColor)
                .setDuration(1500)
            nightSkyAnimator.setEvaluator(ArgbEvaluator())

            val animatorSet = AnimatorSet()
            animatorSet.play(heightAnimator)
                .with(sunsetSkyAnimator) // 같이 작동하는 애니메이션
                .before(nightSkyAnimator) // 이 애니메이션 전에 작동하기
            animatorSet.start()
            shouldRise = true
        } else {
            val heightAnimator = ObjectAnimator
                .ofFloat(sunView, "y", sunYEnd, sunYstart)
                .setDuration(3000)

            heightAnimator.interpolator = AccelerateInterpolator()

            val sunsetSkyAnimator = ObjectAnimator
                .ofInt(skyView, "backgroundColor", sunsetSkyColor, blueSkyColor)
                .setDuration(3000)
            sunsetSkyAnimator.setEvaluator(ArgbEvaluator())

            val nightSkyAnimator = ObjectAnimator
                .ofInt(skyView, "backgroundColor", nightSkyColor, sunsetSkyColor)
                .setDuration(1500)
            nightSkyAnimator.setEvaluator(ArgbEvaluator())

            val animatorSet = AnimatorSet()
            animatorSet.play(nightSkyAnimator)
                .with(heightAnimator) // 같이 작동하는 애니메이션
                .before(sunsetSkyAnimator) // 이 애니메이션 전에 작동하기
            animatorSet.start()
            shouldRise = false
        }
    }
}