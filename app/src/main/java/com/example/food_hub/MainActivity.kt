package com.example.food_hub

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.food_hub.data.FoodApi
import com.example.food_hub.ui.features.auth.AuthScreen
import com.example.food_hub.ui.features.auth.login.LoginScreen
import com.example.food_hub.ui.features.auth.signup.SignUpScreen
import com.example.food_hub.ui.navigation.AuthScreen
import com.example.food_hub.ui.navigation.Home
import com.example.food_hub.ui.navigation.Login
import com.example.food_hub.ui.navigation.SignUp
import com.example.food_hub.ui.theme.FoodHubTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var isShowSplashScreen = true

    @Inject
    lateinit var foodApi: FoodApi

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            setKeepOnScreenCondition { isShowSplashScreen }
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FoodHubTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = AuthScreen,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<SignUp> {
                            SignUpScreen(navController)
                        }
                        composable<AuthScreen> {
                            AuthScreen(navController)
                        }
                        composable<Login> {
                            LoginScreen(navController)
                        }
                        composable<Home> {
                            Box(
                                Modifier
                                    .fillMaxSize()
                                    .background(Color.Red)
                            )
                        }
                    }
                }
            }

            if (::foodApi.isInitialized) {
                Log.d("MainActivity", "FoodApi is initialized")
            }
            CoroutineScope(Dispatchers.IO).launch {
                isShowSplashScreen = false
            }
        }
    }
}