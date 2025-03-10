package com.example.callme

import android.app.Application
import android.text.format.DateUtils
import com.example.callme.core.domain.model.UserCall
import dagger.hilt.android.HiltAndroidApp
import io.getstream.video.android.core.StreamVideo
import io.getstream.video.android.core.StreamVideoBuilder
import io.getstream.video.android.model.User
import io.getstream.video.android.model.UserType

@HiltAndroidApp
class CallMeApp : Application() {

    private var currentName: String? = null
    var client: StreamVideo? = null

    fun initVideoClient(username: String) {
        if (client == null || username != currentName) {
            StreamVideo.removeClient()
            currentName = username

            client = StreamVideoBuilder(
                context = this,
                apiKey = "xrj7y9kcrr5q",
                user = User(
                    id = username,
                    name = username,
                    type = UserType.Guest,
                ),
                token = "dummy token"
            ).build()
        }
    }
}
