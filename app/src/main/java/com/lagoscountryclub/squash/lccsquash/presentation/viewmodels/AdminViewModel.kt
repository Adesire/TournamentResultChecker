package com.lagoscountryclub.squash.lccsquash.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.lagoscountryclub.squash.lccsquash.data.remote.api.request.LoginRequest
import com.lagoscountryclub.squash.lccsquash.data.remote.api.response.UserResponse
import com.lagoscountryclub.squash.lccsquash.domain.model.Game
import com.lagoscountryclub.squash.lccsquash.domain.model.Player
import com.lagoscountryclub.squash.lccsquash.domain.model.RoundData
import com.lagoscountryclub.squash.lccsquash.domain.model.Tournament
import com.lagoscountryclub.squash.lccsquash.domain.usecases.AdminUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(private val useCase: AdminUseCase) : BaseViewModel() {

    private val _loginRequest = mutableStateOf(LoginRequest())
    val loginRequest: LoginRequest
        get() = _loginRequest.value

    private val _user = mutableStateOf(UserResponse())
    val user: UserResponse
        get() = _user.value

    private val _player = mutableStateOf(Player())
    val player: Player
        get() = _player.value

    private val _tournament = mutableStateOf(Tournament())
    val tournament: Tournament
        get() = _tournament.value

    private val _game = mutableStateOf(Game())
    val game: Game
        get() = _game.value

    private val _rounds = mutableListOf<RoundData>()
    val rounds: List<RoundData>
        get() = _rounds


    init {
        useCase.onLoading = ::setLoading

        useCase.onError = { message ->
            message?.let {
                setErrorMessage(it)
            }
        }

    }

    fun updateEmail(email: String) {
        _loginRequest.value = loginRequest.copy(email = email)
    }

    fun updatePassword(password: String) {
        _loginRequest.value = loginRequest.copy(password = password)
    }

    fun updateTournamentName(name: String) {
        if (name.isEmpty() || name.length < 3) {
            setErrorMessage("Tournament name must be at greater than 3 characters")
            return
        }
        _tournament.value = tournament.copy(name = name)
    }

    fun updateTournamentYear(year: String) {
        if (year.isEmpty() || year.length <= 3) {
            setErrorMessage("Tournament year must be at greater than 3 characters")
            return
        }
        _tournament.value = tournament.copy(year = year)
    }

    fun updateTournamentBestOfCount(count: String = "0") {
        val value = count.toInt()
        if (value == 3 || value == 5 || value == 7) {
            _tournament.value = tournament.copy(bestOfCount = value)
            return
        }
    }

    fun updatePlayer(player: Player, isPlayer1: Boolean) {
        if (isPlayer1) {
            _game.value = game.copy(player1 = player)
        } else _game.value = game.copy(player2 = player)
    }

    fun updatePlayerPoints(points: Int, isPlayer1: Boolean) {
        if (isPlayer1) {
            _game.value = game.copy(player1Point = points)
        } else _game.value = game.copy(player2Point = points)
    }

    fun updateRoundsData(bestOfCount: Int) {
        for (i in 0 until bestOfCount) {
            if (rounds.size < bestOfCount) {
                _rounds.add(RoundData())
            }
        }
    }

    private fun updateGameScores() {
        val recordsTransform =
            rounds.joinToString { r ->
                if (r.player1Score > r.player2Score) "1"
                else if (r.player2Score > r.player1Score) "2"
                else "0"
            }
        val scoresTransform =
            rounds.joinToString(separator = ",") { r -> "${r.player1Score}-${r.player2Score}" }
        _game.value = game.copy(record = recordsTransform, scores = scoresTransform)
    }

    fun createPlayer(name: String) {
        if (name.isEmpty() || name.length < 3) {
            setErrorMessage("Player's name must be at greater than 3 characters")
            return
        }
        useCase.createPlayer(name) {
            _player.value = it
        }.launchIn(viewModelScope)
    }

    fun createTournament() {
        if (tournament.bestOfCount !in listOf(3, 5, 7)) {
            setErrorMessage("Invalid Best of count")
            return
        }
        useCase.createTournament(tournament) {
            _tournament.value = it
        }.launchIn(viewModelScope)
    }

    fun createGame() {
        if (game.player1.id == game.player2.id) {
            setErrorMessage("Player 1 and 2 cannot be the same")
            return
        }
        updateGameScores()
        Timber.e("$game")
        useCase.createGame(game) {
            _game.value = it
        }.launchIn(viewModelScope)
    }

    fun login() {
        useCase.login(loginRequest) {
            _user.value = it
        }.launchIn(viewModelScope)
    }

    fun resetCreatedPlayer() {
        _player.value = Player()
    }

    fun resetCreatedTournament() {
        _tournament.value = Tournament()
    }

    fun resetCreatedGame() {
        _game.value = Game()
        _rounds.clear()
    }

}