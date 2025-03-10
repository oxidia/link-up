package com.example.callme.feature_connect.presentation

sealed class ConnectEvent() {
    data class OnUsernameChange(val username: String) : ConnectEvent()
    data object OnConnectClick : ConnectEvent()
}
