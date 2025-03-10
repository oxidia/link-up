package com.example.callme.feature_connect.presentation

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.callme.CallMeApp
import com.example.callme.core.domain.model.Profile
import com.example.callme.core.domain.repository.ProfileRepository
import com.example.callme.core.domain.repository.UserCallRepository
import com.example.callme.core.util.Route
import com.example.callme.core.util.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ConnectViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val userCallRepository: UserCallRepository,
    private val app: Application,
) : ViewModel() {

    var state by mutableStateOf(ConnectState())
        private set

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        initCurrentProfile()
        loadUserCalls()
    }

    fun onEvent(event: ConnectEvent) {
        when (event) {
            is ConnectEvent.OnUsernameChange -> {
                onUsernameChange(event.username)
            }

            is ConnectEvent.OnConnectClick -> {
                onConnectClick()
            }
        }
    }

    private fun onUsernameChange(username: String) {
        state = state.copy(
            username = username
        )
    }

    private fun initCurrentProfile() {
        profileRepository.getProfile()
            .onEach { profile ->
                if (profile == null) {
                    return@onEach
                }

                state = state.copy(
                    currentProfile = profile,
                    username = profile.username
                )
            }
            .launchIn(viewModelScope)
    }

    private fun onConnectClick() {
        viewModelScope.launch {

            (app as CallMeApp).initVideoClient(state.username)

            profileRepository.insertProfile(
                Profile(
                    id = state.currentProfile?.id,
                    username = state.username
                )
            )
            _uiEvent.send(UIEvent.OnNavigate(Route.Call))
        }
    }

    private fun loadUserCalls() {

        userCallRepository.getUserCalls()
            .onEach {
                state = state.copy(
                    userCalls = it
                )
            }
            .launchIn(viewModelScope)
    }
}
