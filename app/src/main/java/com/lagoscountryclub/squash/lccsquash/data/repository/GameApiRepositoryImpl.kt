package com.lagoscountryclub.squash.lccsquash.data.repository

import com.lagoscountryclub.squash.lccsquash.data.Resource
import com.lagoscountryclub.squash.lccsquash.data.remote.GameApiService
import com.lagoscountryclub.squash.lccsquash.data.remote.api.makeRepositoryRequest
import com.lagoscountryclub.squash.lccsquash.domain.converters.toGame
import com.lagoscountryclub.squash.lccsquash.domain.model.Game
import com.lagoscountryclub.squash.lccsquash.domain.repository.GameApiRepository
import javax.inject.Inject

class GameApiRepositoryImpl @Inject constructor(
    private val apiService: GameApiService
) : GameApiRepository {
    override suspend fun getAllGames(tournament: Long?): Resource<List<Game>> =
        makeRepositoryRequest {
            apiService.getAllGames(tournament).data!!.map { it.toGame() }
        }

    override suspend fun getGame(id: Long, tournament: Long?): Resource<Game> =
        makeRepositoryRequest {
            apiService.getAGame(id, tournament).data!!.toGame()
        }

}