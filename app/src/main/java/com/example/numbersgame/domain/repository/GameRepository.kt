package com.example.numbersgame.domain.repository

import com.example.numbersgame.domain.models.GameSettings
import com.example.numbersgame.domain.models.Level
import com.example.numbersgame.domain.models.Question

interface GameRepository {
    fun generateQuestion(
        maxSumValue:Int,
        countOptions:Int
    ): Question
    fun getGameSettings(level: Level): GameSettings
}