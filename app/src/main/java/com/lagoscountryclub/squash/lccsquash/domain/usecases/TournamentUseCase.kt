package com.lagoscountryclub.squash.lccsquash.domain.usecases

import com.lagoscountryclub.squash.lccsquash.data.remote.api.ApiSessionManager
import com.lagoscountryclub.squash.lccsquash.domain.converters.toTournamentRequest
import com.lagoscountryclub.squash.lccsquash.domain.model.Player
import com.lagoscountryclub.squash.lccsquash.domain.model.Tournament
import com.lagoscountryclub.squash.lccsquash.domain.repository.PlayerApiRepository
import com.lagoscountryclub.squash.lccsquash.domain.repository.TournamentApiRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TournamentUseCase @Inject constructor(
    private val playerApiRepository: PlayerApiRepository,
    private val tournamentApiRepository: TournamentApiRepository,
    sessionManager: ApiSessionManager
) : BaseUseCase(sessionManager) {

    fun getAll(onSuccess: (tournaments: List<Tournament>) -> Unit) =
        flow {
            startLoading()
            emit(tournamentApiRepository.getAllTournaments())
        }.evaluateOnEach(onSuccess::invoke).flowOn(coroutineContext)

    fun getTop30Players(id: Long?, onSuccess: (players: List<Player>) -> Unit) = flow {
        startLoading()
        emit(playerApiRepository.getTop30(id))
    }.evaluateOnEach(onSuccess::invoke).flowOn(coroutineContext)

    fun getMostPlayedGames(id: Long, onSuccess: (players: List<Player>) -> Unit) = flow {
        startLoading()
        emit(playerApiRepository.getMostPlayed(id))
    }.evaluateOnEach(onSuccess::invoke).flowOn(coroutineContext)

    fun searchTournament(keywords: String, onSuccess: (tournaments: List<Tournament>) -> Unit) =
        flow {
            startLoading()
            emit(tournamentApiRepository.searchTournament(keywords))
        }.evaluateOnEach(onSuccess::invoke).flowOn(coroutineContext)

    fun getTournament(id: Long?, onSuccess: (tournament: Tournament) -> Unit) = flow {
        startLoading()
        emit(tournamentApiRepository.getTournament(id ?: 0))
    }.evaluateOnEach(onSuccess::invoke).flowOn(coroutineContext)

    fun updateTournament(tournament: Tournament, onSuccess: (tournament: Tournament) -> Unit) =
        flow {
            startLoading()
            emit(
                tournamentApiRepository.updateTournament(
                    tournament.id,
                    tournament.toTournamentRequest()
                )
            )
        }.evaluateOnEach(onSuccess::invoke).flowOn(coroutineContext)
}