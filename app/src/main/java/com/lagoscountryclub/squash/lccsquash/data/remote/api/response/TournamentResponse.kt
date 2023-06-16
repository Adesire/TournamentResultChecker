package com.lagoscountryclub.squash.lccsquash.data.remote.api.response

data class TournamentResponse(
    val id: Long,
    val name: String,
    val year: String,
    val totalGameCount: Int,
    val bestOfCount: Int,
    val rules: List<String>,
    val rulesContent: String?
)
