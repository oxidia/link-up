package com.example.callme.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.callme.core.presentation.theme.CallmeTheme
import com.example.callme.core.util.Route
import com.example.callme.feature_call.representation.CallScreen
import com.example.callme.feature_connect.presentation.ConnectScreen
import dagger.hilt.android.AndroidEntryPoint
import io.getstream.video.android.compose.theme.VideoTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            CallmeTheme {
                Scaffold { paddingValue ->
                    NavHost(
                        navController = navController,
                        startDestination = Route.Connect.route,
                        modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.primaryContainer)
                            .padding(paddingValue)
                    ) {
                        composable(route = Route.Connect.route) {
                            ConnectScreen(
                                navController = navController
                            )
                        }

                        composable(route = Route.Call.route) {
                            VideoTheme {
                                CallScreen(
                                    navController = navController
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
