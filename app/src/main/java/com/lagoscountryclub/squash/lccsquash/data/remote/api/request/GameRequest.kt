package com.lagoscountryclub.squash.lccsquash.data.remote.api.request

data class GameRequest(
    val player1Id: Long,
    val player1Points: Int,
    val player2Id: Long,
    val player2Points: Int,
    val roundRecords: String,
    val roundScores: String,
    val tournamentId: Long
)