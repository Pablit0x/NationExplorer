package com.pscode.app.presentation.screens.shared

sealed class Event {
    data class ShowSnackbarMessage(val message: String) : Event()
}