package com.lagoscountryclub.squash.lccsquash.data.repository

import com.lagoscountryclub.squash.lccsquash.data.Resource
import com.lagoscountryclub.squash.lccsquash.data.remote.PlayerApiService
import com.lagoscountryclub.squash.lccsquash.data.remote.api.ApiSessionManager
import com.lagoscountryclub.squash.lccsquash.data.remote.api.makeRepositoryRequest
import com.lagoscountryclub.squash.lccsquash.domain.converters.toPlayer
import com.lagoscountryclub.squash.lccsquash.domain.model.Player
import com.lagoscountryclub.squash.lccsquash.domain.repository.PlayerApiRepository
import javax.inject.Inject

class PlayerApiRepositoryImpl @Inject constructor(
    private val apiService: PlayerApiService,
    private val sessionManager: ApiSessionManager
) : PlayerApiRepository {
    override suspend fun getAllPlayers(): Resource<List<Player>> = makeRepositoryRequest {
        apiService.getAllPlayers().data!!.map { it.toPlayer() }
    }

    override suspend fun getPlayer(id: Long, tournament: Long?): Resource<Player> =
        makeRepositoryRequest {
            apiService.getPlayer(id, tournament).data!!.toPlayer()
        }

    override suspend fun getTop30(tournament: Long?): Resource<List<Player>> =
        makeRepositoryRequest {
            apiService.getTop30Players(tournament).data!!.map { it.toPlayer() }
        }

    override suspend fun getMostPlayed(tournament: Long): Resource<List<Player>> =
        makeRepositoryRequest {
            apiService.getMostPlayed(tournament).data!!.map { it.toPlayer() }
        }

    override suspend fun searchPlayers(name: String): Resource<List<Player>> =
        makeRepositoryRequest {
            apiService.searchPlayers(name).data!!.map { it.toPlayer() }
        }

}