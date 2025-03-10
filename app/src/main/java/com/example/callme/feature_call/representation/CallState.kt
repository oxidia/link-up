package com.example.callme.feature_call.representation

import com.example.callme.core.domain.model.Profile
import io.getstream.video.android.core.Call

data class CallState(
    val currentProfile: Profile? = null,
    val call: Call? = null,
    val callState: VideoCallState? = null,
    val error: String? = null,
    val startedAt: Long = System.currentTimeMillis(),
)

enum class VideoCallState {
    JOINING,
    ACTIVE,
    ENDED
}
