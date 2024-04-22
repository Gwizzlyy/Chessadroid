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
        val chessBoardView = findViewById<ChessBoardView>(R.id.mainView)  //  finding the view
        chessBoardView.chessInterface = this //  connecting interface and the view
    }

    override fun pieceAt(column: Int, row: Int): Piece? {
        return chessModel.pieceAt(column, row)
    }
}