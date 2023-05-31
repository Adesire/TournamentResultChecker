package com.lagoscountryclub.squash.lccsquash.presentation.composables.bottomnav

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.lagoscountryclub.squash.lccsquash.R
import com.lagoscountryclub.squash.lccsquash.presentation.screens.NavRoutes

sealed class BottomScreen(val route: String, @StringRes val title: Int, @DrawableRes val icon: Int) {
    object Home : BottomScreen(NavRoutes.HOME, R.string.home, R.drawable.baseline_people_24)
    object Rules : BottomScreen(NavRoutes.RULES, R.string.rules, R.drawable.baseline_rule_24)
    object Leaderboard : BottomScreen(
        NavRoutes.LEADERBOARD,
        R.string.leaderboard,
        R.drawable.baseline_leaderboard_24
    )
}

val bottomItems = listOf(BottomScreen.Home, BottomScreen.Rules, BottomScreen.Leaderboard)