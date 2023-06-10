package com.lagoscountryclub.squash.lccsquash.data.remote

import com.lagoscountryclub.squash.lccsquash.data.remote.api.request.TournamentRequest
import com.lagoscountryclub.squash.lccsquash.data.remote.api.response.SuccessResponse
import com.lagoscountryclub.squash.lccsquash.data.remote.api.response.TournamentResponse
import retrofit2.http.*

interface TournamentApiService {

    @GET(ALL)
    suspend fun getAllTournaments(): SuccessResponse<List<TournamentResponse>>

    @GET(TOURNAMENT)
    suspend fun getATournament(
        @Path("id") id: Long = 0
    ): SuccessResponse<TournamentResponse>

    @POST(SEARCH)
    suspend fun searchTournament(
        @Query("name") name: String
    ): SuccessResponse<List<TournamentResponse>>

    @POST(TOURNAMENT_BASE)
    suspend fun createTournament(
        @Body body: TournamentRequest
    ): SuccessResponse<TournamentResponse>

    @PUT(TOURNAMENT)
    suspend fun updateTournament(
        @Path("id") id: Long,
        @Body body: TournamentRequest
    ): SuccessResponse<TournamentResponse>

    @DELETE(TOURNAMENT)
    suspend fun deleteTournament(
        @Path("id") id: Long
    ): SuccessResponse<TournamentResponse>

    companion object {
        const val TOURNAMENT_BASE = "v1/tournament"
        const val ALL = "$TOURNAMENT_BASE/all"
        const val TOURNAMENT = "$TOURNAMENT_BASE/{id}"
        const val SEARCH = "${TOURNAMENT_BASE}/search"
    }
}