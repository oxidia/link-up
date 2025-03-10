package com.example.callme.feature_call.representation.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.getstream.video.android.compose.permission.LaunchCallPermissions
import io.getstream.video.android.compose.theme.VideoTheme
import io.getstream.video.android.compose.ui.components.call.CallAppBar
import io.getstream.video.android.compose.ui.components.call.controls.ControlActions
import io.getstream.video.android.compose.ui.components.call.controls.actions.DefaultOnCallActionHandler
import io.getstream.video.android.compose.ui.components.call.renderer.ParticipantVideo
import io.getstream.video.android.compose.ui.components.call.renderer.RegularVideoRendererStyle
import io.getstream.video.android.core.Call
import io.getstream.video.android.core.call.state.LeaveCall

@Composable
fun ParticipantsGrid(
    call: Call,
    permissions: List<String>,
    context: Context,
    onJoinCall: () -> Unit,
    onLeaveCall: () -> Unit,
    onBackPressCall: () -> Unit,
) {
    val participants by call.state.participants.collectAsState()

    LaunchCallPermissions(
        call = call,
        onPermissionsResult = { permissionsResult ->
            if (permissionsResult.values.contains(false)) {
                val toast = Toast.makeText(
                    context,
                    "Please grant all permissions to use this app.",
                    Toast.LENGTH_LONG
                )
                toast.show()
            } else {
                onJoinCall()
            }
        }
    )

    Scaffold(
        contentColor = VideoTheme.colors.basePrimary,
        topBar = { CallAppBar(call = call) },
        bottomBar = {
            ControlActions(
                modifier = Modifier.fillMaxSize(),
                call = call,
                onCallAction = { action ->
                    if (action == LeaveCall) {
                        onLeaveCall()
                    }

                    DefaultOnCallActionHandler.onCallAction(call, action)
                }
            )
        }
    ) { paddings ->
        LazyVerticalGrid(
            contentPadding = paddings,
            columns = GridCells.Fixed(2),
        ) {
            items(participants, key = { it.sessionId }) { participant ->
                ParticipantVideo(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    call = call,
                    participant = participant,
                    style = RegularVideoRendererStyle(
                        isShowingParticipantLabel = true,
                        labelPosition = Alignment.TopStart,
                        isShowingConnectionQualityIndicator = true,
                        reactionDuration = 500,
                        reactionPosition = Alignment.Center,
                    ),
                )
            }
        }
    }
}
