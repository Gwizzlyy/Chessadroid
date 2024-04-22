package com.gwizz.chessdroid.customviews

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.gwizz.chessdroid.R
import com.gwizz.chessdroid.TAG
import com.gwizz.chessdroid.interfaces.ChessInterface
import com.gwizz.chessdroid.models.ChessModel
import kotlin.math.min

class ChessBoardView(context: Context?, set: AttributeSet?): View(context, set) {
    private val viewScale = .9f  //  to make the board bigger or smaller
    private var initX = 20f  //  margin on the sides of the board
    private var initY = 200f  //  Distance (aka margin) at top and bottom
    private var rectangleSize = 130f
    private val pieceSrc = setOf(
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
    private val bitmap = mutableMapOf<Int, Bitmap>()
    private val dBlue = Color.rgb(113,148,170)
    private val lBlue = Color.rgb(212,224,228)
    private var fromColumn: Int = -1
    private var fromRow: Int = -1
    var chessInterface: ChessInterface? = null
    private var floatingPieceX = -1f
    private var floatingPieceY = -1f

    init {
        populateMap()
    }

    override fun onDraw(canvas: Canvas) {
        canvas ?: return
        val minimum = min(width, height) * viewScale
        rectangleSize = minimum / 8f
        initX = (width - minimum) / 2f
        initY = (height - minimum) / 2f

        initiateChessBoard(canvas)
        insertPieces(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return false
        when (event.action){
            MotionEvent.ACTION_DOWN -> {
                fromColumn = ((event.x - initX) / rectangleSize).toInt()
                fromRow = 7 - ((event.y - initY) / rectangleSize).toInt()  // 7 is to counter sys


            }
            MotionEvent.ACTION_UP -> {
                val column = ((event.x - initX) / rectangleSize).toInt()
                val row = 7 - ((event.y - initY) / rectangleSize).toInt()  // 7 is to counter sys
                Log.d(TAG, "From (${fromColumn}, ${fromRow}) to ($column, $row)")
                chessInterface?.movePiece(fromColumn, fromRow, column, row)

            }
            MotionEvent.ACTION_MOVE -> {
                floatingPieceX = event.x
                floatingPieceY = event.y
                invalidate()
            }
        }
        return true
    }

    private fun initiateChessBoard(canvas: Canvas){  //  Drawing the checkered layout. test
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

    private fun insertPieces(canvas: Canvas){  //  Fetching info from ChessModel and populating
        for (row in 0..7){
            for (col in 0..7){
                if (row != fromRow || col != fromColumn) {
                    chessInterface?.pieceAt(col, row)?.let {  //  "let" checks for null 🤷🏻‍♂️
                        locationSpecifier(canvas, col, row, it.pieceID)
                }

                }
            }
        }

        chessInterface?.pieceAt(fromColumn, fromRow)?.let {
            val map = bitmap[it.pieceID]!!
            canvas.drawBitmap(map, null, RectF(floatingPieceX - rectangleSize/2,
                floatingPieceY - rectangleSize/2,floatingPieceX + rectangleSize/2,
                floatingPieceY + rectangleSize/2), brush)
        }
    }

    private fun locationSpecifier(canvas: Canvas, column: Int, row: Int, pieceNum: Int){
        val map = bitmap[pieceNum]!!
        canvas.drawBitmap(map, null, RectF(initX + column * rectangleSize,
            initY + (7 - row) * rectangleSize,initX + (column + 1) * rectangleSize,
            initY + ((7 - row) + 1) * rectangleSize), brush)
    }

    private fun paintSquare(canvas: Canvas, column: Int, row: Int, isDark: Boolean){
        brush.color = if (isDark) dBlue else lBlue
        canvas.drawRect(initX + column * rectangleSize, initY + row * rectangleSize,
            initX + (column + 1)* rectangleSize, initY + (row + 1) * rectangleSize,
            brush)
    }
}