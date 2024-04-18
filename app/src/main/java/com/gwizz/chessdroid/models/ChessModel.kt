package com.gwizz.chessdroid.models

import com.gwizz.chessdroid.Player
import com.gwizz.chessdroid.Rank

class ChessModel {
    private var inventory = mutableSetOf<Piece>()

    init {
        reset()
    }

    fun reset(){
        inventory.removeAll(inventory)
        for (i in 0..1){
            inventory.add(Piece(0 + i * 7, 0, Player.WHITE, Rank.ROOK))
            inventory.add(Piece(0 + i * 7, 7, Player.BLACK, Rank.ROOK))

            inventory.add(Piece(0 + i * 7, 0, Player.WHITE, Rank.KNIGHT))
            inventory.add(Piece(0 + i * 7, 7, Player.BLACK, Rank.KNIGHT))

            inventory.add(Piece(0 + i * 7, 0, Player.WHITE, Rank.BISHOP))
            inventory.add(Piece(0 + i * 7, 7, Player.BLACK, Rank.BISHOP))
        }
        for (i in 0..7){
            inventory.add((Piece(i, 1,Player.WHITE, Rank.PAWN)))
            inventory.add((Piece(i, 6,Player.BLACK, Rank.PAWN)))
        }

        inventory.add(Piece(3, 0, Player.WHITE, Rank.QUEEN))
        inventory.add(Piece(3, 7, Player.BLACK, Rank.QUEEN))
        inventory.add(Piece(4, 0, Player.WHITE, Rank.KING))
        inventory.add(Piece(4, 7, Player.BLACK, Rank.KING))
    }
}