package com.pscode.app.presentation.screens.shared

sealed class Event {
    data class ShowSnackbarMessage(val message: String) : Event()
    data class ShowSnackbarMessageWithAction(
        val message: String,
        val actionLabel: String,
        val action: () -> Unit
    ) : Event()
}