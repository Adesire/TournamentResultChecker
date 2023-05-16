package com.lagoscountryclub.squash.lccsquash.domain.converters

import com.lagoscountryclub.squash.lccsquash.data.remote.api.response.PlayerResponse
import com.lagoscountryclub.squash.lccsquash.domain.model.Player

fun PlayerResponse.toPlayer(): Player {
    return Player(this.name)
}