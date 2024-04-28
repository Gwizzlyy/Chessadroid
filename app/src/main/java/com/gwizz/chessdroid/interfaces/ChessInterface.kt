package com.gwizz.chessdroid.interfaces

import com.gwizz.chessdroid.models.Piece
//  Homer s00045809
//  Since it is not recommended to call functions in views, I bridge my view, model, and controller
//  using an Interface
interface ChessInterface {
    fun pieceAt(column: Int, row: Int): Piece?
    fun movePiece(fromColumn: Int, fromRow: Int, toColumn: Int, toRow: Int)
}