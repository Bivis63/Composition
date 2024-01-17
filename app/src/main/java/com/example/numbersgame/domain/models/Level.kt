package com.example.numbersgame.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Level :Parcelable{

    TEST,EASE,NORMAL,HARD
}