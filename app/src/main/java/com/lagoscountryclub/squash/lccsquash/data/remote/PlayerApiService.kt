package com.lagoscountryclub.squash.lccsquash.data.remote

import com.lagoscountryclub.squash.lccsquash.data.remote.api.request.PlayerRequest
import com.lagoscountryclub.squash.lccsquash.data.remote.api.response.PlayerResponse
import com.lagoscountryclub.squash.lccsquash.data.remote.api.response.PlayersResponse
import com.lagoscountryclub.squash.lccsquash.data.remote.api.response.SuccessResponse
import retrofit2.http.*

interface PlayerApiService {

    @GET(ALL)
    suspend fun getAllPlayers(): SuccessResponse<List<PlayersResponse>>

    @GET(PLAYER)
    suspend fun getPlayer(
        @Path("id") id: Long,
        @Query("tournament") tournament: Long?
    ): SuccessResponse<PlayerResponse>

    @GET(TOP_30)
    suspend fun getTop30Players(
        @Query("tournament") tournament: Long?
    ): SuccessResponse<List<PlayerResponse>>

    @GET(MOST_PLAYED)
    suspend fun getMostPlayed(
        @Query("tournament") tournament: Long
    ): SuccessResponse<List<PlayerResponse>>

    @POST(SEARCH)
    suspend fun searchPlayers(
        @Query("name") name: String
    ): SuccessResponse<List<PlayersResponse>>

    @POST(PLAYER_BASE)
    suspend fun createPlayer(
        @Body body: PlayerRequest
    ): SuccessResponse<PlayerResponse>

    @PUT(PLAYER)
    suspend fun updatePlayer(
        @Path("id") id: Long,
        @Body body: PlayerRequest
    ): SuccessResponse<PlayerResponse>

    @DELETE(PLAYER)
    suspend fun deletePlayer(
        @Path("id") id: Long
    ): SuccessResponse<PlayerResponse>

    companion object {
        const val PLAYER_BASE = "v1/player"
        const val ALL = "$PLAYER_BASE/all"
        const val PLAYER = "$PLAYER_BASE/{id}"
        const val TOP_30 = "$PLAYER_BASE/top30"
        const val MOST_PLAYED = "$PLAYER_BASE/most-played"
        const val SEARCH = "$PLAYER_BASE/search"
    }
}