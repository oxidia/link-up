package com.example.callme.core.util


sealed class Route(val route: String) {
    data object Connect : Route("connect_screen")
    data object Call : Route("call_screen")
}
