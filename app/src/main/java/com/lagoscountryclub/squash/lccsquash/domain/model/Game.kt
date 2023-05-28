package com.lagoscountryclub.squash.lccsquash.domain.model

data class Game(
    val id: Long = 0,
    val player1: Player = Player(),
    val player2: Player = Player(),
    val player1Point: Int = 0,
    val player2Point: Int = 0,
    val record: String = "",
    val scores: String = ""
)
