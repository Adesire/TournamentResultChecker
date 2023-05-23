package com.lagoscountryclub.squash.lccsquash.data.remote

import com.lagoscountryclub.squash.lccsquash.data.remote.api.response.SuccessResponse
import com.lagoscountryclub.squash.lccsquash.data.remote.api.response.TournamentResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TournamentApiService {

    @GET(ALL)
    fun getAllTournaments(): SuccessResponse<List<TournamentResponse>>

    @GET(TOURNAMENT)
    fun getATournament(
        @Path("id") id: Long
    ): SuccessResponse<TournamentResponse>

    @POST(SEARCH)
    fun searchTournament(
        @Query("name") name: String
    ): SuccessResponse<List<TournamentResponse>>

    companion object {
        private const val TOURNAMENT_BASE = "v1/tournament"
        const val ALL = "$TOURNAMENT_BASE/all"
        const val TOURNAMENT = "$TOURNAMENT_BASE/{id}"
        const val SEARCH = "${TOURNAMENT_BASE}/search"
    }
}