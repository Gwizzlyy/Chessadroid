package com.gwizz.chessdroid.models

import com.gwizz.chessdroid.Player
import com.gwizz.chessdroid.Rank

data class Piece(
    var column: Int, var row: Int, val player: Player, val rank: Rank,
    val pieceID: Int){

}
