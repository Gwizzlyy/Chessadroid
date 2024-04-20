package com.gwizz.chessdroid.models

import com.gwizz.chessdroid.Player
import com.gwizz.chessdroid.Rank

data class Piece(val column: Int, val row: Int, val player: Player, val rank: Rank,
                 val pieceID: Int){

}
