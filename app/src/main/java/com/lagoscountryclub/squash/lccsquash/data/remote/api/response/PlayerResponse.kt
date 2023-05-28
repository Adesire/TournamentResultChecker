package com.lagoscountryclub.squash.lccsquash.data.remote.api.response

data class PlayerResponse(
    val id: Long,
    val name: String,
    val points: Int,
    val gamesPlayed: Int,
    val tournamentName: String,
    val games: List<GameResponse>
)
