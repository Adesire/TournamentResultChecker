package com.lagoscountryclub.squash.lccsquash.domain.converters

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
    return Tournament(id = this.id, name = this.name)
}