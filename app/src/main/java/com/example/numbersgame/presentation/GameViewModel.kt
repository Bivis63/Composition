package com.example.numbersgame.presentation

import android.app.Application
import android.content.Context
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.numbersgame.R
import com.example.numbersgame.data.GameRepositoryImpl
import com.example.numbersgame.domain.models.GameResult
import com.example.numbersgame.domain.models.GameSettings
import com.example.numbersgame.domain.models.Level
import com.example.numbersgame.domain.models.Question
import com.example.numbersgame.domain.usecase.GenerateQuestionUseCase
import com.example.numbersgame.domain.usecase.GetGameSettingsUseCase

class GameViewModel(
    private val application: Application,
    private val level: Level
) : ViewModel() {

    private lateinit var gameSettings: GameSettings
    private var timer: CountDownTimer? = null

    private val repository = GameRepositoryImpl

    private val generateQuestionUseCase = GenerateQuestionUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    private var _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String> = _formattedTime

    private var _question = MutableLiveData<Question>()
    val question: LiveData<Question> = _question

    private var _percentOfRightAnswer = MutableLiveData<Int>()
    val percentOfRightAnswer: LiveData<Int> = _percentOfRightAnswer

    private var _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String> = _progressAnswers

    private var _enoughCountOfRightAnswers = MutableLiveData<Boolean>()
    val enoughCountOfRightAnswers: LiveData<Boolean> = _enoughCountOfRightAnswers

    private var _enoughPercentOfRightAnswers = MutableLiveData<Boolean>()
    val enoughPercentOfRightAnswers: LiveData<Boolean> = _enoughPercentOfRightAnswers

    private var _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int> = _minPercent

    private var _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult> = _gameResult


    private var countOfRightAnswers = 0
    private var countOfQuestions = 0

    init {
        startGame()
    }

    private fun startGame() {
        getGameSettings()
        startTimer()
        generateQuestion()
        updateProgress()

    }

    fun chooseAnswer(number: Int) {
        checkAnswer(number)
        updateProgress()
        generateQuestion()
    }

    private fun updateProgress() {
        val percent = calculatePercentOfRightAnswer()
        _percentOfRightAnswer.value = percent
        _progressAnswers.value = String.format(
            application.getString(R.string.progress_answers),
            countOfRightAnswers,
            gameSettings.minCountOfRightAnswers
        )
        _enoughCountOfRightAnswers.value =
            countOfRightAnswers >= gameSettings.minCountOfRightAnswers
        _enoughPercentOfRightAnswers.value =
            percent >= gameSettings.minPercentOfRightAnswers
    }

    private fun calculatePercentOfRightAnswer(): Int {
        if (countOfQuestions == 0) {
            return 0
        }
        return ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }


    private fun checkAnswer(number: Int) {
        val rightAnswer = question.value?.rightAnswer
        if (number == rightAnswer) {
            countOfRightAnswers++
        }
        countOfQuestions++
    }

    private fun getGameSettings() {
        this.gameSettings = getGameSettingsUseCase(level)
        _minPercent.value = gameSettings.minPercentOfRightAnswers
    }

    private fun startTimer() {
        timer = object : CountDownTimer(
            gameSettings.gameTimeInSeconds * MILLIS_IN_SECONDS, MILLIS_IN_SECONDS
        ) {
            override fun onTick(p0: Long) {
                _formattedTime.value = formattedTime(p0)
            }

            override fun onFinish() {
                finishGame()
            }
        }
        timer?.start()
    }

    private fun generateQuestion() {

        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    private fun formattedTime(p0: Long): String {
        val seconds = p0 / MILLIS_IN_SECONDS
        val minutes = seconds / 60
        val remainingSeconds = seconds % SECONDS_IN_MINUTES
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            winner = _enoughCountOfRightAnswers.value == true && _enoughPercentOfRightAnswers.value == true,
            countOfRightAnswers = countOfRightAnswers,
            countOfQuestion = countOfQuestions,
            gameSettings = gameSettings
        )
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }

    companion object {
        private const val MILLIS_IN_SECONDS = 1000L
        private const val SECONDS_IN_MINUTES = 60
    }
}