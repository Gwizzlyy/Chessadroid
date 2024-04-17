package com.gwizz.chessdroid.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class ChessBoardView(context: Context?, set: AttributeSet?): View(context, set) {
    final val initX = 20f
    final val initY = 200f
    final val rectangleSize = 130f

    override fun onDraw(canvas: Canvas) {
        val brush = Paint()
        for (i in 0..7) {
            for (j in 0..7) {
                brush.color = if ((i + j) % 2 == 1) Color.rgb(113,148,170) else
                    Color.rgb(212,224,228)
                canvas.drawRect(initX + i * rectangleSize, initY + j * rectangleSize,
                    initX + (i + 1)* rectangleSize, initY + (j + 1) * rectangleSize,
                    brush)
            }
        }
    }
}