package com.example.callme.feature_call.representation

import io.getstream.video.android.core.StreamVideo


sealed class CallEvent() {
    data class OnJoinCall(val client: StreamVideo) : CallEvent()
    data class OnDisconnectClick(val client: StreamVideo) : CallEvent()
}
