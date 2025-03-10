package com.example.callme.feature_call.representation.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.getstream.video.android.compose.permission.rememberCallPermissionsState
import io.getstream.video.android.compose.ui.components.call.CallAppBar
import io.getstream.video.android.compose.ui.components.call.activecall.CallContent
import io.getstream.video.android.compose.ui.components.call.controls.actions.DefaultOnCallActionHandler
import io.getstream.video.android.core.Call
import io.getstream.video.android.core.call.state.LeaveCall

@Composable
fun WithCallContent(
    call: Call,
    permissions: List<String>,
    context: Context,
    onJoinCall: () -> Unit,
    onLeaveCall: () -> Unit,
    onBackPressCall: () -> Unit,
) {
    CallContent(
        call = call,
        modifier = Modifier
            .fillMaxSize(),
        permissions = rememberCallPermissionsState(
            call = call,
            permissions = permissions,
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
        ),
        appBarContent = {
            CallAppBar(
                call = call,
                onBackPressed = onBackPressCall,
                onCallAction = { action ->
                    if (action == LeaveCall) {
                        onLeaveCall()
                    }

                    DefaultOnCallActionHandler.onCallAction(call, action)
                }
            )
        },
    )
}
