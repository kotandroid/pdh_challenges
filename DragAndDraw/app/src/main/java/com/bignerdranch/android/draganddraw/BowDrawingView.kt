package com.bignerdranch.android.draganddraw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

private const val TAG = "BoxDrawingView"
private const val KEY_SUPER = "STATE_SUPER"
private const val KEY_SIZE = "STATE_SIZE"

class BoxDrawingView(context: Context, attrs: AttributeSet? = null): View(context, attrs) {

    init {
        isSaveEnabled = true
    }

    private var currentBox:Box? = null
    private val boxen = mutableListOf<Box>() // 박스 담는 리스트
    private val boxPaint = Paint().apply {
        color = 0x22ff0000
    }
    private val backgroundPaint = Paint().apply {
        color = 0xfff8efe0.toInt()
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPaint(backgroundPaint)
        boxen.forEach { box ->
            canvas.drawRect(box.left, box.top, box.right, box.bottom, boxPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val current = PointF(event.x, event.y)
        var action = ""
        when (event.action){
            MotionEvent.ACTION_DOWN -> {
                action = "ACTION_DOWN"
                currentBox = Box(current).also {
                    boxen.add(it)
                }
            }
            MotionEvent.ACTION_MOVE -> {
                action = "ACTION_MOVE"
                updateCurrentBox(current)
            }
            MotionEvent.ACTION_UP -> {
                action = "ACTION_UP"
                updateCurrentBox(current)
                currentBox = null
            }
            MotionEvent.ACTION_CANCEL -> {
                action = "ACTION_CANCEL"
                currentBox = null
            }
        }
        Log.i(TAG, "$action at x=${current.x}, y=${current.y}")
        return true
    }

    private fun updateCurrentBox(current: PointF) {
        currentBox?.let{
            it.end = current
            invalidate()
        }
    }

    override fun onSaveInstanceState(): Parcelable {

        val superState = super.onSaveInstanceState()
        val state = Bundle().apply {
            putParcelable(KEY_SUPER, superState)
            putInt(KEY_SIZE, boxen.size)
            for (i in 0 until boxen.size){
                putFloat("BOX_${i}_T", boxen[i].top)
                putFloat("BOX_${i}_B", boxen[i].bottom)
                putFloat("BOX_${i}_L", boxen[i].left)
                putFloat("BOX_${i}_R", boxen[i].right)
            }
        }

        return state
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle) {
            val boxen_size = state.getInt(KEY_SIZE)
            for (i in 0 until boxen_size){
                boxen.add(Box(
                    // start
                    PointF(
                        state.getFloat("BOX_${i}_L"),
                        state.getFloat("BOX_${i}_T")
                    )
                ).apply {
                    end = PointF(
                        state.getFloat("BOX_${i}_R"),
                        state.getFloat("BOX_${i}_B")
                    )
                })
            }
            super.onRestoreInstanceState(state.getParcelable(KEY_SUPER))
        } else {
            super.onRestoreInstanceState(state)
        }
    }
}