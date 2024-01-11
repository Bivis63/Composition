package com.example.numbersgame.domain.usecase

import com.example.numbersgame.domain.models.GameSettings
import com.example.numbersgame.domain.models.Level
import com.example.numbersgame.domain.repository.GameRepository

class GetGameSettingsUseCase (private val repository: GameRepository){

    operator fun invoke(level: Level):GameSettings{
        return repository.getGameSettings(level)
    }
}