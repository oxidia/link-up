package com.example.callme.feature_call.representation.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import io.getstream.video.android.compose.permission.LaunchCallPermissions
import io.getstream.video.android.compose.ui.components.call.renderer.ParticipantVideo
import io.getstream.video.android.core.Call

@Composable
fun BasicCall(
    call: Call,
    context: Context,
    onJoinCall: () -> Unit,
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

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp)
    ) {
        items(participants) { participant ->
            ParticipantVideo(
                modifier = Modifier
                    .height(128.dp)
                    .clip(RoundedCornerShape(16.dp)),
                call = call,
                participant = participant,
            )
        }
    }
}
