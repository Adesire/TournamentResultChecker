package com.lagoscountryclub.squash.lccsquash.data.remote.api.response

data class GameResponse(
    val id: Long,
    val player1: PlayersResponse,
    val player2: PlayersResponse,
    val player1Points: Int,
    val player2Points: Int,
    val roundRecords: String,//“110” -> means player 1 won first and second round, for best of 3
    val roundScores: String//“11-5, 11-7, nil” means the score for each round
)
