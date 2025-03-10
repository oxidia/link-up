package com.example.callme.feature_call.representation

import android.Manifest
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.callme.CallMeApp
import com.example.callme.core.util.UIEvent
import com.example.callme.feature_call.representation.components.WithCallContent

@Composable
fun CallScreen(
    navController: NavController,
    viewModel: CallViewModel = hiltViewModel(),
) {
    val state = viewModel.state
    val context = LocalContext.current
    val app = context.applicationContext as CallMeApp
    val client = app.client!!

    val basePermissions = listOf(
        Manifest.permission.CAMERA,
        Manifest.permission.RECORD_AUDIO,
    )

    val bluetoothConnectPermission = if (Build.VERSION.SDK_INT >= 31) {
        listOf(Manifest.permission.BLUETOOTH_CONNECT)
    } else {
        emptyList()
    }

    val notificationPermission = if (Build.VERSION.SDK_INT >= 33) {
        listOf(Manifest.permission.POST_NOTIFICATIONS)
    } else {
        emptyList()
    }

    LaunchedEffect(Unit) {
        val call = client.call("default", "main-room")
        viewModel.setCall(call)
    }

    LaunchedEffect(Unit) {
        viewModel.onEvent(
            CallEvent.OnJoinCall(client)
        )

        viewModel.uiEvent.collect { event ->
            when (event) {
                is UIEvent.PopBackStack -> {
                    navController.navigateUp()
                }

                else -> Unit
            }
        }
    }

    if (state.call == null) {
        return
    }

    WithCallContent(
        context = app,
        call = state.call,
        permissions = basePermissions + bluetoothConnectPermission + notificationPermission,
        onJoinCall = {
            viewModel.onEvent(CallEvent.OnJoinCall(client))
        },
        onLeaveCall = {
            viewModel.onEvent(CallEvent.OnDisconnectClick(client))
        },
        onBackPressCall = {
            viewModel.onEvent(CallEvent.OnDisconnectClick(client))
        },
    )
}
