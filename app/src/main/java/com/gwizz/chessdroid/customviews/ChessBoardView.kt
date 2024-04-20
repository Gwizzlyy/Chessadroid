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
import com.gwizz.chessdroid.interfaces.ChessInterface
import com.gwizz.chessdroid.models.ChessModel

class ChessBoardView(context: Context?, set: AttributeSet?): View(context, set) {
    private final val initX = 20f
    private final val initY = 200f
    private final val rectangleSize = 130f
    final val pieceSrc = setOf(
        R.drawable.white_king,
        R.drawable.white_knight,
        R.drawable.white_bishop,
        R.drawable.white_rook,
        R.drawable.black_bishop,
        R.drawable.black_knight,
        R.drawable.black_rook,
        R.drawable.black_king,
        R.drawable.white_queen,
        R.drawable.black_pawn,
        R.drawable.white_pawn,
        R.drawable.black_queen)
    private val brush = Paint()
    private final val bitmap = mutableMapOf<Int, Bitmap>()
    private final val dBlue = Color.rgb(113,148,170)
    private final val lBlue = Color.rgb(212,224,228)

    var chessInterface: ChessInterface? = null

    init {
        populateMap()
    }

    override fun onDraw(canvas: Canvas) {
        initiateChessBoard(canvas)
        insertPieces(canvas)
    }

    private fun initiateChessBoard(canvas: Canvas?){  //  Drawing the checkered layout.
        for (i in 0..7) {
            for (j in 0..7) {
                paintSquare(canvas, i, j, (i + j) % 2 == 1)
            }
        }
    }

    private fun populateMap(){
        pieceSrc.forEach {
            bitmap[it] = BitmapFactory.decodeResource(resources, it)
        }
    }

    private fun insertPieces(canvas: Canvas?){  //  Fetching info from ChessModel and populating
        for (row in 0..7){
            for (col in 0..7){
                chessInterface?.pieceAt(col, row)?.let {  //  "let" checks for null 🤷🏻‍♂️
                    locationSpecifier(canvas, col, row, it.pieceID)
                }
            }
        }
    }

    private fun locationSpecifier(canvas: Canvas?, column: Int, row: Int, pieceNum: Int){
        val map = bitmap[pieceNum]!!
        canvas?.drawBitmap(map, null, RectF(initX + column * rectangleSize,
            initY + (7 - row) * rectangleSize,initX + (column + 1) * rectangleSize,
            initY + ((7 - row) + 1) * rectangleSize), brush)
    }

    private fun paintSquare(canvas: Canvas?, column: Int, row: Int, isDark: Boolean){
        brush.color = if (isDark) dBlue else lBlue
        canvas?.drawRect(initX + column * rectangleSize, initY + row * rectangleSize,
            initX + (column + 1)* rectangleSize, initY + (row + 1) * rectangleSize,
            brush)
    }
}