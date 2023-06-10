package com.lagoscountryclub.squash.lccsquash.domain.usecases

import com.lagoscountryclub.squash.lccsquash.data.remote.api.ApiSessionManager
import com.lagoscountryclub.squash.lccsquash.domain.model.Game
import com.lagoscountryclub.squash.lccsquash.domain.model.Player
import com.lagoscountryclub.squash.lccsquash.domain.repository.GameApiRepository
import com.lagoscountryclub.squash.lccsquash.domain.repository.PlayerApiRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PlayerUseCase @Inject constructor(
    private val playerApiRepository: PlayerApiRepository,
    private val gameRepository: GameApiRepository,
    sessionManager: ApiSessionManager
) : BaseUseCase(sessionManager) {
    fun getAllPlayers(onSuccess: (players: List<Player>) -> Unit) = flow {
        emit(playerApiRepository.getAllPlayers())
    }.evaluateOnEach {
        onSuccess.invoke(it)
    }.flowOn(coroutineContext)

    fun getPlayer(id: Long, tournament: Long?, onSuccess: (players: Player) -> Unit) = flow {
        emit(playerApiRepository.getPlayer(id, tournament))
    }.evaluateOnEach {
        onSuccess.invoke(it)
    }.flowOn(coroutineContext)

    fun getPlayerGame(id: Long, tournament: Long, onSuccess: (game: Game) -> Unit) = flow {
        emit(gameRepository.getGame(id, tournament))
    }.evaluateOnEach {
        onSuccess.invoke(it)
    }.flowOn(coroutineContext)
}