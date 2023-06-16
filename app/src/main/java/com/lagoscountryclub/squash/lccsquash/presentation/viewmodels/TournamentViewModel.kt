package com.lagoscountryclub.squash.lccsquash.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.lagoscountryclub.squash.lccsquash.domain.model.Player
import com.lagoscountryclub.squash.lccsquash.domain.model.Tournament
import com.lagoscountryclub.squash.lccsquash.domain.usecases.TournamentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TournamentViewModel @Inject constructor(private val useCase: TournamentUseCase) :
    BaseViewModel() {

    private val _tournaments = mutableStateListOf<Tournament>()
    val tournaments: List<Tournament>
        get() = _tournaments

    private val _tournament = mutableStateOf(Tournament())
    val tournament: State<Tournament>
        get() = _tournament

    private val _players = mutableStateListOf<Player>()
    val players: List<Player>
        get() = _players

    private val _updateDone = mutableStateOf(false)
    val updateDone: State<Boolean>
        get() = _updateDone

    init {
        useCase.onLoading = ::setLoading

        useCase.onError = { message ->
            message?.let {
                setErrorMessage(it)
            }
        }
        getAll()

        if (tournament.value.id == -1L) {
            getTournament()//getLatestTournament on first-intialization
        }
    }

    fun updateTournament(tournament: Tournament) {
        _tournament.value = tournament
    }

    fun updateRulesContent(content: String) {
        _tournament.value = tournament.value.copy(rulesContent = content)
    }

    fun resetUpdate() {
        _updateDone.value = false
    }

    fun getAll() {
        useCase.getAll {
            _tournaments.clear()
            _tournaments.addAll(it)
        }.launchIn(viewModelScope)
    }

    fun getTournament(id: Long? = null) {
        useCase.getTournament(id) {
            _tournament.value = it
        }.launchIn(viewModelScope)
    }

    fun getTop30() {
        val t = tournament.value
        val id = if (t.id != -1L) t.id else 0
        useCase.getTop30Players(id) {
            _players.clear()
            _players.addAll(it)
        }.launchIn(viewModelScope)
    }

    fun updateTournament() {
        val t = tournament.value
        Timber.e(t.toString())
        useCase.updateTournament(t) {
            updateTournament(it)
            _updateDone.value = true
        }.launchIn(viewModelScope)
    }

    fun showAdminViews() {
        setAdmin(!useCase.checkJwtTokenExpired())
    }
}