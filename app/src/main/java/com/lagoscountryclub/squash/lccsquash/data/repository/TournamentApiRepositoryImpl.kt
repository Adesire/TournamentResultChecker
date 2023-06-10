package com.lagoscountryclub.squash.lccsquash.data.repository

import com.lagoscountryclub.squash.lccsquash.data.Resource
import com.lagoscountryclub.squash.lccsquash.data.remote.TournamentApiService
import com.lagoscountryclub.squash.lccsquash.data.remote.api.makeRepositoryRequest
import com.lagoscountryclub.squash.lccsquash.data.remote.api.request.TournamentRequest
import com.lagoscountryclub.squash.lccsquash.domain.converters.toTournament
import com.lagoscountryclub.squash.lccsquash.domain.model.Tournament
import com.lagoscountryclub.squash.lccsquash.domain.repository.TournamentApiRepository
import javax.inject.Inject

class TournamentApiRepositoryImpl @Inject constructor(
    private val apiService: TournamentApiService
) : TournamentApiRepository {
    override suspend fun getAllTournaments(): Resource<List<Tournament>> =
        makeRepositoryRequest {
            apiService.getAllTournaments().data!!.map { it.toTournament() }
        }

    override suspend fun getTournament(id: Long): Resource<Tournament> =
        makeRepositoryRequest {
            apiService.getATournament(id).data!!.toTournament()
        }

    override suspend fun searchTournament(name: String): Resource<List<Tournament>> =
        makeRepositoryRequest {
            apiService.searchTournament(name).data!!.map { it.toTournament() }
        }

    override suspend fun createTournament(body: TournamentRequest): Resource<Tournament> =
        makeRepositoryRequest {
            apiService.createTournament(body).data!!.toTournament()
        }

    override suspend fun updateTournament(id: Long, body: TournamentRequest): Resource<Tournament> =
        makeRepositoryRequest {
            apiService.updateTournament(id, body).data!!.toTournament()
        }

    override suspend fun deleteTournament(id: Long): Resource<Tournament> =
        makeRepositoryRequest {
            apiService.deleteTournament(id).data!!.toTournament()
        }

}