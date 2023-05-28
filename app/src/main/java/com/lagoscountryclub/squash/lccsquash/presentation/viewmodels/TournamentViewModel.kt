package com.lagoscountryclub.squash.lccsquash.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.lagoscountryclub.squash.lccsquash.domain.model.Tournament
import com.lagoscountryclub.squash.lccsquash.domain.usecases.TournamentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
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

    init {
        useCase.onLoading = ::setLoading

        useCase.onError = { message ->
            message?.let {
                setErrorMessage(it)
            }
        }
        getAll()
    }

    private fun getAll() {
        useCase.getAll {
            _tournaments.clear()
            _tournaments.addAll(it)
        }.launchIn(viewModelScope)
    }

    fun getTournament(id: Long) {
        useCase.getTournament(id) {
            _tournament.value = it
        }.launchIn(viewModelScope)
    }
}