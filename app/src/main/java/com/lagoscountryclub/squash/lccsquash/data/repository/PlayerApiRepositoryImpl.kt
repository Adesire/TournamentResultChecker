package com.lagoscountryclub.squash.lccsquash.data.repository

import com.lagoscountryclub.squash.lccsquash.data.remote.PlayerApiService
import com.lagoscountryclub.squash.lccsquash.data.remote.api.ApiSessionManager
import com.lagoscountryclub.squash.lccsquash.domain.repository.PlayerApiRepository
import javax.inject.Inject

class PlayerApiRepositoryImpl @Inject constructor(private val apiService: PlayerApiService,
                                                  private val sessionManager: ApiSessionManager
) : PlayerApiRepository {

}