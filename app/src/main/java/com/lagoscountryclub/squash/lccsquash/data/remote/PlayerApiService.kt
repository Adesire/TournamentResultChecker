package com.lagoscountryclub.squash.lccsquash.data.remote

import com.lagoscountryclub.squash.lccsquash.data.remote.api.response.PlayerResponse
import com.lagoscountryclub.squash.lccsquash.data.remote.api.response.PlayersResponse
import com.lagoscountryclub.squash.lccsquash.data.remote.api.response.SuccessResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PlayerApiService {

    @GET(ALL)
    fun getAllPlayers(): SuccessResponse<List<PlayersResponse>>

    @GET(PLAYER)
    fun getPlayer(
        @Path("id") id: Long,
        @Query("tournament") tournament: Long?
    ): SuccessResponse<PlayerResponse>

    @GET(TOP_30)
    fun getTop30Players(
        @Query("tournament") tournament: Long?
    ): SuccessResponse<List<PlayerResponse>>

    @GET(MOST_PLAYED)
    fun getMostPlayed(
        @Query("tournament") tournament: Long
    ): SuccessResponse<List<PlayerResponse>>

    @POST(SEARCH)
    fun searchPlayers(
        @Query("name") name: String
    ): SuccessResponse<List<PlayersResponse>>

    companion object {
        private const val PLAYER_BASE = "v1/player"
        const val ALL = "$PLAYER_BASE/all"
        const val PLAYER = "$PLAYER_BASE/{id}"
        const val TOP_30 = "$PLAYER_BASE/top30"
        const val MOST_PLAYED = "$PLAYER_BASE/most-played"
        const val SEARCH = "$PLAYER_BASE/search"
    }
}