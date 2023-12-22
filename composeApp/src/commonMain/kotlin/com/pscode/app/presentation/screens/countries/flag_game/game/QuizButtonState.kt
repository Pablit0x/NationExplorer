package com.pscode.app.presentation.screens.countries.flag_game.game

sealed class QuizButtonState {
    data class NEXT(val onNextClick: () -> Unit) : QuizButtonState()
    data class FINISH(val onFinishClick: () -> Unit) : QuizButtonState()
}
