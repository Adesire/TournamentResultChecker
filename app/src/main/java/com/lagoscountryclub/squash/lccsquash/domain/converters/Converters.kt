package com.lagoscountryclub.squash.lccsquash.domain.converters

import com.lagoscountryclub.squash.lccsquash.data.remote.api.request.GameRequest
import com.lagoscountryclub.squash.lccsquash.data.remote.api.request.TournamentRequest
import com.lagoscountryclub.squash.lccsquash.data.remote.api.response.GameResponse
import com.lagoscountryclub.squash.lccsquash.data.remote.api.response.PlayerResponse
import com.lagoscountryclub.squash.lccsquash.data.remote.api.response.PlayersResponse
import com.lagoscountryclub.squash.lccsquash.data.remote.api.response.TournamentResponse
import com.lagoscountryclub.squash.lccsquash.domain.model.Game
import com.lagoscountryclub.squash.lccsquash.domain.model.Player
import com.lagoscountryclub.squash.lccsquash.domain.model.Tournament

fun PlayersResponse.toPlayer(): Player {
    return Player(id = this.id, name = this.name)
}

fun PlayerResponse.toPlayer(): Player {
    return Player(
        id = this.id,
        name = this.name,
        points = this.points,
        gamesPlayed = this.gamesPlayed,
        tournamentName = this.tournamentName,
        games = this.games.map { it.toGame() }
    )
}

fun GameResponse.toGame(): Game {
    return Game(
        id = this.id,
        player1 = this.player1.toPlayer(),
        player2 = this.player2.toPlayer(),
        player1Point = this.player1Points,
        player2Point = this.player2Points,
        record = this.roundRecords,
        scores = this.roundScores
    )
}

fun TournamentResponse.toTournament(): Tournament {
    return Tournament(
        id = this.id,
        name = this.name,
        year = this.year,
        gameCount = this.totalGameCount,
        bestOfCount = this.bestOfCount,
        rules = this.rules
    )
}

fun Tournament.toTournamentRequest(): TournamentRequest {
    return TournamentRequest(
        name = this.name,
        tournamentYear = this.year,
        bestOfCount = this.bestOfCount,
        rules = this.rules
    )
}

fun Game.toGameRequest(tournamentId: Long = 0): GameRequest {
    return GameRequest(
        player1Id = this.player1.id,
        player2Id = this.player2.id,
        player1Points = this.player1Point,
        player2Points = this.player2Point,
        roundRecords = this.record,
        roundScores = this.scores,
        tournamentId = tournamentId
    )
}