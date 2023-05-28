package com.lagoscountryclub.squash.lccsquash.domain.model

data class Player(
    val id: Long = 0,
    val name: String = "",
    val points: Int = 0,
    val gamesPlayed: Int = 0,
    val tournamentName: String = "",
    val games: List<Game> = emptyList()
)
