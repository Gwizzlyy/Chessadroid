package com.gwizz.chessdroid.models

import com.gwizz.chessdroid.Player
import com.gwizz.chessdroid.R
import com.gwizz.chessdroid.Rank

class ChessModel {
    private var inventory = mutableSetOf<Piece>()

    init {
        reset()
    }

    fun reset(){
        inventory.removeAll(inventory)
        for (i in 0..1){
            inventory.add(Piece(0 + i * 7, 0, Player.WHITE, Rank.ROOK, R.drawable.white_rook))
            inventory.add(Piece(0 + i * 7, 7, Player.BLACK, Rank.ROOK, R.drawable.black_rook))

            inventory.add(Piece(0 + i * 7, 0, Player.WHITE, Rank.KNIGHT, R.drawable.white_knight))
            inventory.add(Piece(0 + i * 7, 7, Player.BLACK, Rank.KNIGHT, R.drawable.black_knight))

            inventory.add(Piece(0 + i * 7, 0, Player.WHITE, Rank.BISHOP, R.drawable.white_bishop))
            inventory.add(Piece(0 + i * 7, 7, Player.BLACK, Rank.BISHOP, R.drawable.black_bishop))
        }
        for (i in 0..7){
            inventory.add((Piece(i, 1,Player.WHITE, Rank.PAWN, R.drawable.white_pawn)))
            inventory.add((Piece(i, 6,Player.BLACK, Rank.PAWN, R.drawable.black_pawn)))
        }

        inventory.add(Piece(3, 0, Player.WHITE, Rank.QUEEN, R.drawable.white_queen))
        inventory.add(Piece(3, 7, Player.BLACK, Rank.QUEEN, R.drawable.black_queen))
        inventory.add(Piece(4, 0, Player.WHITE, Rank.KING, R.drawable.white_king))
        inventory.add(Piece(4, 7, Player.BLACK, Rank.KING, R.drawable.black_king))
    }

    fun localise(column: Int, row: Int): Piece?{
        for (piece in inventory){
            if (column == piece.column && row == piece.row){
                return piece
            }
        }
        return null
    }
}