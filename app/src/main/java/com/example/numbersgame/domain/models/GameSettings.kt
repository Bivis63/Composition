package com.example.numbersgame.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class GameSettings (
    val maxSumValue: Int,
    val minCountOfRightAnswers: Int,
    val minPercentOfRightAnswers: Int,
    val gameTimeInSeconds: Int
):Parcelable