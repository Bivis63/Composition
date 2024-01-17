package com.example.numbersgame.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class GameResult(
    val winner: Boolean,
    val countOfRightAnswers:Int,
    val countOfQuestion: Int,
    val gameSettings: GameSettings
) :Parcelable
