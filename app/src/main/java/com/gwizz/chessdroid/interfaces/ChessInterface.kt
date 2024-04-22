package com.gwizz.chessdroid.interfaces

import com.gwizz.chessdroid.models.Piece

interface ChessInterface {
    fun pieceAt(column: Int, row: Int): Piece?
    fun movePiece(fromColumn: Int, fromRow: Int, toColumn: Int, toRow: Int)
}