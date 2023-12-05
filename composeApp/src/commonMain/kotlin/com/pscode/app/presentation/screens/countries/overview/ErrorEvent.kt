package com.pscode.app.presentation.screens.countries.overview

sealed class ErrorEvent {
    data class ShowSnackbarMessage(val message: String) : ErrorEvent()
}