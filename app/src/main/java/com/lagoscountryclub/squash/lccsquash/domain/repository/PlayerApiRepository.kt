package com.lagoscountryclub.squash.lccsquash.domain.repository

import com.lagoscountryclub.squash.lccsquash.data.Resource
import com.lagoscountryclub.squash.lccsquash.data.remote.api.request.PlayerRequest
import com.lagoscountryclub.squash.lccsquash.domain.model.Player

interface PlayerApiRepository {
    suspend fun getAllPlayers(): Resource<List<Player>>
    suspend fun getPlayer(id: Long, tournament: Long?): Resource<Player>
    suspend fun getTop30(tournament: Long?): Resource<List<Player>>
    suspend fun getMostPlayed(tournament: Long): Resource<List<Player>>
    suspend fun searchPlayers(name: String): Resource<List<Player>>
    suspend fun createPlayer(body: PlayerRequest): Resource<Player>
    suspend fun updatePlayer(id: Long, body: PlayerRequest): Resource<Player>
    suspend fun deletePlayer(id: Long): Resource<Player>
}