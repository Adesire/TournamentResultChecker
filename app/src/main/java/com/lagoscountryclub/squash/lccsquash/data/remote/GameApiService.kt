package com.lagoscountryclub.squash.lccsquash.data.remote

import com.lagoscountryclub.squash.lccsquash.data.remote.api.request.GameRequest
import com.lagoscountryclub.squash.lccsquash.data.remote.api.response.GameResponse
import com.lagoscountryclub.squash.lccsquash.data.remote.api.response.SuccessResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface GameApiService {

    @GET(ALL)
    suspend fun getAllGames(@Query("tournament") tournament: Long?): SuccessResponse<List<GameResponse>>

    @GET(GAME)
    suspend fun getAGame(
        @Path("id") id: Long,
        @Query("tournament") tournament: Long?
    ): SuccessResponse<GameResponse>


    @POST(GAME_BASE)
    suspend fun createAGame(@Body request: GameRequest): SuccessResponse<GameResponse>

    @PUT(GAME)
    suspend fun updateAGame(@Path("id") id: Long, body: GameRequest): SuccessResponse<GameResponse>

    @DELETE(GAME)
    suspend fun deleteAGame(@Path("id") id: Long): SuccessResponse<GameResponse>

    companion object {
        const val GAME_BASE = "v1/game"
        const val ALL = "$GAME_BASE/all"
        const val GAME = "$GAME_BASE/{id}"
    }
}