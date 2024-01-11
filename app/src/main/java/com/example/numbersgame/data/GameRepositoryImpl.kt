package com.example.numbersgame.data

import com.example.numbersgame.domain.models.GameSettings
import com.example.numbersgame.domain.models.Level
import com.example.numbersgame.domain.models.Question
import com.example.numbersgame.domain.repository.GameRepository
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

object GameRepositoryImpl : GameRepository {

    private const val MIN_SUM_VALUE = 2
    private const val MIN_ANSWER_VALUE = 1

    override fun generateQuestion(maxSumValue: Int, countOptions: Int): Question {
        val sum = Random.nextInt(MIN_SUM_VALUE, maxSumValue + 1)
        val visibleNumber = Random.nextInt(MIN_ANSWER_VALUE, sum)
        val options = HashSet<Int>()
        val rightAnswer = sum - visibleNumber
        options.add(rightAnswer)
        val from = max(rightAnswer - countOptions, MIN_ANSWER_VALUE)
        val to = min(maxSumValue, rightAnswer + countOptions)
        while (options.size < countOptions) {
            options.add(Random.nextInt(from, to))
        }
        return Question(sum, visibleNumber, options.toList())
    }

    override fun getGameSettings(level: Level): GameSettings {
        return when (level) {
            Level.TEST -> {
                GameSettings(
                    maxSumValue = 10,
                    minCountOfRightAnswers = 3,
                    minPercentOfRightAnswers = 50,
                    gameTimeInSeconds = 8
                )
            }

            Level.EASE -> {
                GameSettings(
                    maxSumValue = 10,
                    minCountOfRightAnswers = 10,
                    minPercentOfRightAnswers = 70,
                    gameTimeInSeconds = 60
                )
            }

            Level.NORMAL -> {
                GameSettings(
                    maxSumValue = 20,
                    minCountOfRightAnswers = 20,
                    minPercentOfRightAnswers = 80,
                    gameTimeInSeconds = 40
                )
            }

            Level.HARD -> {
                GameSettings(
                    maxSumValue = 30,
                    minCountOfRightAnswers = 30,
                    minPercentOfRightAnswers = 90,
                    gameTimeInSeconds = 40
                )
            }
        }
    }
}