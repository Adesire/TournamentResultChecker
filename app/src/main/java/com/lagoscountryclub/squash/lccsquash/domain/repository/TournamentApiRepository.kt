package com.lagoscountryclub.squash.lccsquash.domain.repository

import com.lagoscountryclub.squash.lccsquash.data.Resource
import com.lagoscountryclub.squash.lccsquash.domain.model.Tournament

interface TournamentApiRepository {
    suspend fun getAllTournaments(): Resource<List<Tournament>>
    suspend fun getTournament(id: Long): Resource<Tournament>
    suspend fun searchTournament(name: String): Resource<List<Tournament>>
}