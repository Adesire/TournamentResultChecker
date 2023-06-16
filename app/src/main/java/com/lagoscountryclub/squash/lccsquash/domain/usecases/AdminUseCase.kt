package com.lagoscountryclub.squash.lccsquash.domain.usecases

import com.lagoscountryclub.squash.lccsquash.data.remote.api.ApiSessionManager
import com.lagoscountryclub.squash.lccsquash.data.remote.api.request.LoginRequest
import com.lagoscountryclub.squash.lccsquash.data.remote.api.request.PlayerRequest
import com.lagoscountryclub.squash.lccsquash.data.remote.api.response.UserResponse
import com.lagoscountryclub.squash.lccsquash.domain.converters.toGameRequest
import com.lagoscountryclub.squash.lccsquash.domain.converters.toTournamentRequest
import com.lagoscountryclub.squash.lccsquash.domain.model.Game
import com.lagoscountryclub.squash.lccsquash.domain.model.Player
import com.lagoscountryclub.squash.lccsquash.domain.model.Tournament
import com.lagoscountryclub.squash.lccsquash.domain.repository.AuthApiRepository
import com.lagoscountryclub.squash.lccsquash.domain.repository.GameApiRepository
import com.lagoscountryclub.squash.lccsquash.domain.repository.PlayerApiRepository
import com.lagoscountryclub.squash.lccsquash.domain.repository.TournamentApiRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AdminUseCase @Inject constructor(
    private val authApiRepository: AuthApiRepository,
    private val playerApiRepository: PlayerApiRepository,
    private val gameRepository: GameApiRepository,
    private val tournamentApiRepository: TournamentApiRepository,
    sessionManager: ApiSessionManager
) : BaseUseCase(sessionManager) {

    fun login(loginRequest: LoginRequest, onSuccess: (user: UserResponse) -> Unit) = flow {
        startLoading()
        emit(authApiRepository.login(loginRequest))
    }.evaluateOnEach {
        onSuccess.invoke(it)
    }.flowOn(coroutineContext)

    fun createPlayer(name: String, onSuccess: (player: Player) -> Unit) = flow {
        startLoading()
        emit(playerApiRepository.createPlayer(PlayerRequest(name)))
    }.evaluateOnEach {
        onSuccess.invoke(it)
    }.flowOn(coroutineContext)

    fun createTournament(tournament: Tournament, onSuccess: (Tournament) -> Unit) = flow {
        startLoading()
        emit(tournamentApiRepository.createTournament(tournament.toTournamentRequest()))
    }.evaluateOnEach {
        onSuccess.invoke(it)
    }.flowOn(coroutineContext)

    fun createGame(game: Game, onSuccess: (game: Game) -> Unit) = flow {
        startLoading()
        emit(gameRepository.createGame(game.toGameRequest()))
    }.evaluateOnEach {
        onSuccess.invoke(it)
    }.flowOn(coroutineContext)
}