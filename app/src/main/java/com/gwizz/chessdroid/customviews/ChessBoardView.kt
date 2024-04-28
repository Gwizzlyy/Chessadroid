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
import com.gwizz.chessdroid.models.Piece
import kotlin.math.min

//  Homer s00045809
class ChessBoardView(context: Context?, set: AttributeSet?): View(context, set) {
    private val viewScale = .9f  //  to make the board bigger or smaller
    private var initX = 20f  //  margin on the sides of the board
    private var initY = 200f  //  Distance (aka margin) at top and bottom
    private var rectangleSize = 130f  //  Determines the size of the rectangle
    private val pieceSrc = setOf(  //  A set of all the IDs of the pieces to use when drawing board
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
    //  Class to use on Bitmaps and canvases
    private val brush = Paint()
    private val bitmap = mutableMapOf<Int, Bitmap>()  //  Bitmaps help display while specifying dimenstions
    //  Colour values for the board
    private val dBlue = Color.rgb(113,148,170)
    private val lBlue = Color.rgb(212,224,228)
    //  Initiate an invalid Column x Row values to later reset and modify when moving pieces
    private var fromColumn: Int = -1
    private var fromRow: Int = -1
    //  Linking the chess interface with the view
    var chessInterface: ChessInterface? = null
    //  To allow the piece hover while moving
    private var floatingPieceX = -1f
    private var floatingPieceY = -1f
    private var floatingPieceMap: Bitmap? = null
    private var floatingPiece: Piece? = null

    init {
        populateMap()
    }
    //  Activity Life Cycle to show the default board view
    override fun onDraw(canvas: Canvas) {
        canvas ?: return
        val minimum = min(width, height) * viewScale
        rectangleSize = minimum / 8f
        initX = (width - minimum) / 2f
        initY = (height - minimum) / 2f

        initiateChessBoard(canvas)
        insertPieces(canvas)
    }
    //  Built in function like onClick but allows movement, up, and down detection.
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event ?: return false
        when (event.action){
            //  To know which piece is currently being held
            MotionEvent.ACTION_DOWN -> {
                fromColumn = ((event.x - initX) / rectangleSize).toInt()
                fromRow = 7 - ((event.y - initY) / rectangleSize).toInt()  // 7 is to inverse

                chessInterface?.pieceAt(fromColumn, fromRow)?.let {
                    floatingPiece = it
                    floatingPieceMap = bitmap[it.pieceID]
                }
            }
            //  Gives value for where the piece was dropped
            MotionEvent.ACTION_UP -> {
                val column = ((event.x - initX) / rectangleSize).toInt()
                val row = 7 - ((event.y - initY) / rectangleSize).toInt()  // 7 is to counter sys
                Log.d(TAG, "From (${fromColumn}, ${fromRow}) to ($column, $row)")
                chessInterface?.movePiece(fromColumn, fromRow, column, row)
                floatingPiece = null
                floatingPieceMap = null

            }
            //  Detects movement and allows us to use the floating piece animation
            MotionEvent.ACTION_MOVE -> {
                floatingPieceX = event.x
                floatingPieceY = event.y
                invalidate()
            }
        }
        return true
    }

    private fun initiateChessBoard(canvas: Canvas){  //  Drawing the checkered layout.
        for (i in 0..7) {
            for (j in 0..7) {
                paintSquare(canvas, i, j, (i + j) % 2 == 1)
            }
        }
    }
    //  Taking each piece and populating the bitmap with the values
    private fun populateMap(){
        pieceSrc.forEach {
            bitmap[it] = BitmapFactory.decodeResource(resources, it)
        }
    }
    //  Model communicates to send piece proper location on the board
    private fun insertPieces(canvas: Canvas){  //  Fetching info from ChessModel and populating
        for (row in 0..7){
            for (col in 0..7){
                chessInterface?.pieceAt(col, row)?.let {
                    if (it != floatingPiece){
                        locationSpecifier(canvas, col, row, it.pieceID)
                    }
                }
            }
        }
        //  Floating animation when moving a piece
        floatingPieceMap?.let {
            canvas.drawBitmap(it, null, RectF(floatingPieceX - rectangleSize/2,
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
    //  Where colour values are fed and drawn
    private fun paintSquare(canvas: Canvas, column: Int, row: Int, isDark: Boolean){
        brush.color = if (isDark) dBlue else lBlue
        canvas.drawRect(initX + column * rectangleSize, initY + row * rectangleSize,
            initX + (column + 1)* rectangleSize, initY + (row + 1) * rectangleSize,
            brush)
    }
}