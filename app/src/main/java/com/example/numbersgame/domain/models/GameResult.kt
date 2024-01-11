package com.example.numbersgame.domain.models

data class GameResult(
    val winner: Boolean,
    val countOfRightAnswers:Int,
    val countOfQuestion: Int,
    val gameSettings: GameSettings
)
