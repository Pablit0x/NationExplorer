package com.pscode.app.presentation.screens.shared

sealed class ErrorEvent {
    data class ShowSnackbarMessage(val message: String) : ErrorEvent()
}