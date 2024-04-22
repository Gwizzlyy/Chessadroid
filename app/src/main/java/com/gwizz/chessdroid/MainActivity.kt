package com.gwizz.chessdroid

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.gwizz.chessdroid.customviews.ChessBoardView
import com.gwizz.chessdroid.interfaces.ChessInterface
import com.gwizz.chessdroid.models.ChessModel
import com.gwizz.chessdroid.models.Piece

const val TAG = "MainActivity"
class MainActivity : AppCompatActivity(), ChessInterface {

    var chessModel = ChessModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        //  Establishing connection between interface
        findViewById<ChessBoardView>(R.id.mainView).chessInterface = this
    }

    override fun pieceAt(column: Int, row: Int): Piece? {
        return chessModel.pieceAt(column, row)
    }

    override fun movePiece(fromColumn: Int, fromRow: Int, toColumn: Int, toRow: Int) {
        chessModel.movePiece(fromColumn, fromRow, toColumn, toRow)
        findViewById<ChessBoardView>(R.id.mainView).invalidate()
    }
}