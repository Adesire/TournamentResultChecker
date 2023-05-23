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
    return Player(id = this.id, name = this.name)
}

fun GameResponse.toGame(): Game {
    return Game(id = this.id)
}

fun TournamentResponse.toTournament(): Tournament {
    return Tournament(id = this.id)
}