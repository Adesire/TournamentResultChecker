package com.lagoscountryclub.squash.lccsquash.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.lagoscountryclub.squash.lccsquash.domain.model.Game
import com.lagoscountryclub.squash.lccsquash.domain.model.Player
import com.lagoscountryclub.squash.lccsquash.domain.model.Tournament
import com.lagoscountryclub.squash.lccsquash.domain.usecases.PlayerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(private val useCase: PlayerUseCase) : BaseViewModel() {

    private val _players = mutableStateOf<List<Player>>(emptyList())
    val players: List<Player>
        get() = _players.value

    private val _playersFiltered = mutableStateListOf<Player>()
    val playersFiltered: List<Player>
        get() = _playersFiltered

    private val _player = mutableStateOf(Player())
    val player: State<Player>
        get() = _player

    private val _game = mutableStateOf(Game())
    val game: State<Game>
        get() = _game

    private val _tournament = mutableStateOf(Tournament())
    val tournament: State<Tournament>
        get() = _tournament


    init {
        useCase.onLoading = ::setLoading

        useCase.onError = { message ->
            message?.let {
                setErrorMessage(it)
            }
        }

        getAllPlayers()
    }

    fun getAllPlayers() {
        useCase.getAllPlayers {
            _players.value = it
        }.launchIn(viewModelScope)
    }

    fun getPlayer(id: Long) {
        useCase.getPlayer(id, tournament.value.id) {
            _player.value = it
        }.launchIn(viewModelScope)
    }

    fun getPlayerGame(id: Long) {
        useCase.getPlayerGame(id, tournament.value.id) {
            _game.value = it
        }.launchIn(viewModelScope)
    }

    fun filterPlayers(value: String) {
        _playersFiltered.clear()
        _playersFiltered.addAll(players.filter { it.name.lowercase().contains(value.lowercase()) })
    }

    fun selectedTournament(tournament: Tournament) {
        _tournament.value = tournament
    }

}