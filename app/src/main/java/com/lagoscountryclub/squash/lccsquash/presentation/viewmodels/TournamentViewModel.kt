package com.lagoscountryclub.squash.lccsquash.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.lagoscountryclub.squash.lccsquash.domain.usecases.TournamentUseCase
import javax.inject.Inject

class TournamentViewModel @Inject constructor(private val useCase: TournamentUseCase) : ViewModel() {

}