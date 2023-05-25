package com.lagoscountryclub.squash.lccsquash.data

import com.lagoscountryclub.squash.lccsquash.data.remote.GameApiService
import com.lagoscountryclub.squash.lccsquash.data.remote.api.response.GameResponse
import com.lagoscountryclub.squash.lccsquash.data.remote.api.response.PlayersResponse
import com.lagoscountryclub.squash.lccsquash.data.remote.api.response.SuccessResponse
import com.lagoscountryclub.squash.lccsquash.data.repository.GameApiRepositoryImpl
import com.lagoscountryclub.squash.lccsquash.domain.converters.toGame
import com.lagoscountryclub.squash.lccsquash.domain.model.Game
import com.lagoscountryclub.squash.lccsquash.utils.BaseUnitTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GameApiRepositoryTest : BaseUnitTest() {

    private val service: GameApiService = mockk()
    private val repository = GameApiRepositoryImpl(service)
    private val gamesResponse: GameResponse = GameResponse(
        1L, PlayersResponse(1L, "P1"),
        PlayersResponse(2L, "P2"), 3, 2,
        "110", "11-5, 11-7, nil"
    )
    private val gamesResponses: SuccessResponse<List<GameResponse>> = SuccessResponse(
        data = listOf(gamesResponse)
    )
    private val games: List<Game> = gamesResponses.data!!.map { it.toGame() }
    private val tournamentId = 1L
    private val gameId = 1L

    @Test
    fun verifyAllGamesCalled() = runTest {
        repository.getAllGames(tournamentId)
        verify(exactly = 1) { service.getAllGames(tournamentId) }
    }

    @Test
    fun testGetAllGames() = runTest {
        every { service.getAllGames(tournamentId) } returns gamesResponses

        val response = repository.getAllGames(tournamentId)
        assertEquals(Success(games), response)
    }

    @Test
    fun verifyGetGameCalled() = runTest {
        repository.getGame(gameId, tournamentId)
        verify(exactly = 1) { service.getAGame(gameId, tournamentId) }
    }

    @Test
    fun testGetGame() = runTest {
        every {
            service.getAGame(
                gameId,
                tournamentId
            )
        } returns SuccessResponse(data = gamesResponse)

        val response = repository.getGame(gameId, tournamentId)
        assertEquals(Success(games[0]), response)
    }

}