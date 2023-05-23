package com.lagoscountryclub.squash.lccsquash.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.lagoscountryclub.squash.lccsquash.domain.usecases.PlayerUseCase
import javax.inject.Inject

class PlayerViewModel @Inject constructor(private val useCase: PlayerUseCase) : ViewModel() {

}