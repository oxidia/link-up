package com.example.callme.core.util

sealed class UIEvent {
    data object PopBackStack: UIEvent()
    data class OnNavigate(val screen: Route) : UIEvent()
    data class ShowSnackbar(
        val message: String,
        val action: String? = null,
    ): UIEvent()
}
