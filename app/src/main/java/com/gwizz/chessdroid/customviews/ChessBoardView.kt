package com.gwizz.chessdroid.customviews

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.gwizz.chessdroid.R

class ChessBoardView(context: Context?, set: AttributeSet?): View(context, set) {
    private final val initX = 20f
    private final val initY = 200f
    private final val rectangleSize = 130f
    final val pieceSrc = setOf(
        R.drawable.white_king,
        R.drawable.white_knight,
        R.drawable.white_pawn,
        R.drawable.white_queen,
        R.drawable.white_bishop,
        R.drawable.white_rook,
        R.drawable.black_bishop,
        R.drawable.black_king,
        R.drawable.black_knight,
        R.drawable.black_pawn,
        R.drawable.black_rook,
        R.drawable.black_queen)
    private val brush = Paint()
    private final val bitmap = mutableMapOf<Int, Bitmap>()

    init {
        populateMap()
    }

    override fun onDraw(canvas: Canvas) {
        initiateChessBoard(canvas)
        insertPieces(canvas)
    }

    private fun initiateChessBoard(canvas: Canvas?){

        for (i in 0..7) {
            for (j in 0..7) {
                brush.color = if ((i + j) % 2 == 1) Color.rgb(113,148,170) else
                    Color.rgb(212,224,228)
                canvas?.drawRect(initX + i * rectangleSize, initY + j * rectangleSize,
                    initX + (i + 1)* rectangleSize, initY + (j + 1) * rectangleSize,
                    brush)
            }
        }
    }

    private fun populateMap(){
        pieceSrc.forEach {
            bitmap[it] = BitmapFactory.decodeResource(resources, it)
        }
    }

    private fun insertPieces(canvas: Canvas?){
        locationSpecifier(canvas, 0, 0, R.drawable.white_rook)
        locationSpecifier(canvas, 0, 1, R.drawable.white_pawn)
    }

    private fun locationSpecifier(canvas: Canvas?, column: Int, row: Int, pieceNum: Int){
        val map = bitmap[pieceNum]!!
        canvas?.drawBitmap(map, null, RectF(initX + column * rectangleSize,
            initY + (7 - row) * rectangleSize, (column + 1) * rectangleSize,
            initY + ((7 - row)+ 1) * rectangleSize), brush)
    }
}