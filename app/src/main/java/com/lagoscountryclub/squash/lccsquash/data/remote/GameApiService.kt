package com.lagoscountryclub.squash.lccsquash.data.remote

import com.lagoscountryclub.squash.lccsquash.data.remote.api.response.GameResponse
import com.lagoscountryclub.squash.lccsquash.data.remote.api.response.SuccessResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GameApiService {

    @GET(ALL)
    fun getAllGames(@Query("tournament") tournament: Long?): SuccessResponse<List<GameResponse>>

    @GET(GAME)
    fun getAGame(
        @Path("id") id: Long,
        @Query("tournament") tournament: Long?
    ): SuccessResponse<GameResponse>

    companion object {
        private const val GAME_BASE = "v1/game"
        const val ALL = "$GAME_BASE/all"
        const val GAME = "$GAME_BASE/{id}"
    }
}