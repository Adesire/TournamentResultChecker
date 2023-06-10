package com.lagoscountryclub.squash.lccsquash.domain.repository

import com.lagoscountryclub.squash.lccsquash.data.Resource
import com.lagoscountryclub.squash.lccsquash.data.remote.api.request.GameRequest
import com.lagoscountryclub.squash.lccsquash.domain.model.Game

interface GameApiRepository {
    suspend fun getAllGames(tournament: Long?): Resource<List<Game>>
    suspend fun getGame(id: Long, tournament: Long?): Resource<Game>
    suspend fun createGame(body: GameRequest): Resource<Game>
    suspend fun updateGame(id: Long, body: GameRequest): Resource<Game>
    suspend fun deleteGame(id: Long): Resource<Game>
}