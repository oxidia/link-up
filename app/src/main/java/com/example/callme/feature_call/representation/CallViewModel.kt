package com.example.callme.feature_call.representation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.callme.core.domain.model.UserCall
import com.example.callme.core.domain.repository.ProfileRepository
import com.example.callme.core.domain.repository.UserCallRepository
import com.example.callme.core.util.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.video.android.core.Call
import io.getstream.video.android.core.StreamVideo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CallViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val userCallRepository: UserCallRepository,
) : ViewModel() {

    var state by mutableStateOf(CallState())
        private set

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        initCurrentProfile()
    }

    fun onEvent(event: CallEvent) {
        when (event) {
            is CallEvent.OnJoinCall -> {
                joinCall(event.client)
            }

            is CallEvent.OnDisconnectClick -> {
                state.call?.leave()
                event.client.logOut()
                state = state.copy(callState = VideoCallState.ENDED)

                viewModelScope.launch {
                    userCallRepository.insertUserCall(
                        UserCall(
                            startedAt = state.startedAt,
                            endedAt = System.currentTimeMillis()
                        )
                    )
                }

                sendUIEvent(UIEvent.PopBackStack)
            }
        }
    }

    private fun initCurrentProfile() {
        profileRepository.getProfile()
            .onEach { profile ->
                if (profile == null) {
                    return@onEach
                }

                state = state.copy(
                    currentProfile = profile,
                )
            }
            .launchIn(viewModelScope)
    }

    private fun joinCall(client: StreamVideo) {
        if (state.callState == VideoCallState.ACTIVE) {
            return
        }

        viewModelScope.launch {
            val shouldCreate = client
                .queryCalls(filters = emptyMap())
                .getOrNull()
                ?.calls
                ?.isEmpty() == true

            state.call?.let { call ->
                call.join(create = shouldCreate)
                    .onSuccess {
                        state = state.copy(
                            callState = VideoCallState.ACTIVE,
                            error = null
                        )
                    }
                    .onError {
                        state = state.copy(
                            error = it.message,
                            callState = null
                        )
                    }
            }
        }
    }

    fun setCall(call: Call) {
        state = state.copy(
            call = call
        )
    }

    private fun sendUIEvent(event: UIEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}
