package com.lagoscountryclub.squash.lccsquash.domain.model

data class Tournament(
    val id: Long = 0,
    val name: String = "",
    val year: String = "",
    val gameCount: Int = 0,
    val bestOfCount: Int = 0,
    val rules: List<String> = emptyList()
)
