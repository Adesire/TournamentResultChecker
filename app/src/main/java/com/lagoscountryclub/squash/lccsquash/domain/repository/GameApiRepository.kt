package com.lagoscountryclub.squash.lccsquash.domain.repository

import com.lagoscountryclub.squash.lccsquash.data.Resource
import com.lagoscountryclub.squash.lccsquash.domain.model.Game

interface GameApiRepository {
    suspend fun getAllGames(tournament: Long?): Resource<List<Game>>
    suspend fun getGame(id: Long, tournament: Long?): Resource<Game>
}