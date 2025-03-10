package com.example.callme.feature_connect.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.callme.core.util.UIEvent
import com.example.callme.feature_connect.presentation.components.CallHistoryItem
import com.example.callme.feature_connect.presentation.components.TextField

@Composable
fun ConnectScreen(
    navController: NavController,
    viewModel: ConnectViewModel = hiltViewModel(),
) {
    val state = viewModel.state

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                is UIEvent.OnNavigate -> {
                    navController.navigate(uiEvent.screen.route)
                }

                else -> Unit
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer
            )
            .statusBarsPadding()
            .padding(
                horizontal = 20.dp
            ),
    ) {
        Spacer(Modifier.height(16.dp))

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Username"
            )
            Spacer(Modifier.height(8.dp))
            TextField(
                value = state.username,
                onValueChange = { username ->
                    viewModel.onEvent(ConnectEvent.OnUsernameChange(username))
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(Modifier.height(16.dp))

        Button(
            modifier = Modifier.align(Alignment.End),
            onClick = {
                viewModel.onEvent(ConnectEvent.OnConnectClick)
            }
        ) {
            Text(
                text = "Connect"
            )
        }

        LazyColumn {
            items(state.userCalls) { userCall ->
                Spacer(Modifier.height(8.dp))

                CallHistoryItem(
                    userCall = userCall
                )
            }
        }
    }
}
