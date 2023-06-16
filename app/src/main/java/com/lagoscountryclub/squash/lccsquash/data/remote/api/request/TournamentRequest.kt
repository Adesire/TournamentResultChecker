package com.lagoscountryclub.squash.lccsquash.data.remote.api.request

data class TournamentRequest(
    val bestOfCount: Int,
    val name: String,
    val rules: List<String>,
    val rulesContent: String,
    val tournamentYear: String
)