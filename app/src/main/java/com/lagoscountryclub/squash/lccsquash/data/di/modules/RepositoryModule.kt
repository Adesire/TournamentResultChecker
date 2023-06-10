package com.lagoscountryclub.squash.lccsquash.data.di.modules

import com.lagoscountryclub.squash.lccsquash.data.repository.AuthApiRepositoryImpl
import com.lagoscountryclub.squash.lccsquash.data.repository.GameApiRepositoryImpl
import com.lagoscountryclub.squash.lccsquash.data.repository.PlayerApiRepositoryImpl
import com.lagoscountryclub.squash.lccsquash.data.repository.TournamentApiRepositoryImpl
import com.lagoscountryclub.squash.lccsquash.domain.repository.AuthApiRepository
import com.lagoscountryclub.squash.lccsquash.domain.repository.GameApiRepository
import com.lagoscountryclub.squash.lccsquash.domain.repository.PlayerApiRepository
import com.lagoscountryclub.squash.lccsquash.domain.repository.TournamentApiRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindPlayerRepository(playerApiRepositoryImpl: PlayerApiRepositoryImpl): PlayerApiRepository

    @Binds
    abstract fun bindGameRepository(gameApiRepositoryImpl: GameApiRepositoryImpl): GameApiRepository

    @Binds
    abstract fun bindTournamentRepository(tournamentApiRepositoryImpl: TournamentApiRepositoryImpl): TournamentApiRepository

    @Binds
    abstract fun bindAuthRepository(authApiRepositoryImpl: AuthApiRepositoryImpl): AuthApiRepository
}
