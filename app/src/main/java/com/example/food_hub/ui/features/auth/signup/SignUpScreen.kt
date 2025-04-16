package com.example.food_hub.ui.features.auth.signup

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.food_hub.R
import com.example.food_hub.ui.AppTextField
import com.example.food_hub.ui.GroupSocialButtons
import com.example.food_hub.ui.navigation.AuthScreen
import com.example.food_hub.ui.navigation.Home
import com.example.food_hub.ui.navigation.Login
import com.example.food_hub.ui.navigation.SignUp
import com.example.food_hub.ui.theme.Colors
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignUpScreen(navController: NavController, viewModel: SignUpViewModel = hiltViewModel()) {
    val name = viewModel.name.collectAsStateWithLifecycle()
    val email = viewModel.email.collectAsStateWithLifecycle()
    val password = viewModel.password.collectAsStateWithLifecycle()
    val errorMessage = remember { mutableStateOf<String?>(null) }
    val loading = remember { mutableStateOf(false) }

    val uiState = viewModel.uiState.collectAsState()
    when (uiState.value) {
        is SignUpViewModel.SignUpEvent.Error -> {
            // show error
            loading.value = false
            errorMessage.value = "Failed"
        }

        is SignUpViewModel.SignUpEvent.Loading -> {
            // show loading
            loading.value = true
            errorMessage.value = null
        }

        else -> {
            // do nothing
            loading.value = false
            errorMessage.value = null
        }
    }

    val context = LocalContext.current

    LaunchedEffect(true) {
        viewModel.navigationEvent.collectLatest { event ->
            when (event) {
                is SignUpViewModel.SignUpNavigationEvent.NavigateToHome -> {
                    Toast.makeText(
                        context,
                        "Login successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate(Home) {
                        popUpTo<AuthScreen> {
                            inclusive = true
                        }
                    }
                }

                is SignUpViewModel.SignUpNavigationEvent.NavigateToLogin -> {
                    navController.navigate(Login) {
                        popUpTo<SignUp> {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.sign_up_background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.weight(1f))
            Text(
                text = stringResource(id = R.string.sign_up),
                fontSize = 32.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(20.dp))
            AppTextField(
                value = name.value, onValueChange = { viewModel.onNameChange(it) },
                label = {
                    Text(text = stringResource(id = R.string.name), color = Color.Gray)
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(29.dp))
            AppTextField(
                value = email.value,
                onValueChange = { viewModel.onEmailChange(it) },
                label = {
                    Text(text = stringResource(id = R.string.e_mail), color = Color.Gray)
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.size(29.dp))
            AppTextField(
                value = password.value,
                onValueChange = { viewModel.onPasswordChange(it) },
                label = {
                    Text(text = stringResource(id = R.string.password), color = Color.Gray)
                },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                trailingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_eye),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = errorMessage.value ?: "", color = Color.Red)
            Button(
                onClick = viewModel::onSignUpClick, modifier = Modifier.height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Colors.Primary)
            ) {
                Box {
                    AnimatedContent(
                        targetState = loading.value,
                        transitionSpec = {
                            fadeIn(animationSpec = tween(300)) + scaleIn(initialScale = 0.8f) togetherWith
                                    fadeOut(animationSpec = tween(300)) + scaleOut(targetScale = 0.8f)
                        }, label = ""
                    ) { target ->
                        if (target) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier
                                    .padding(horizontal = 32.dp)
                                    .size(24.dp)
                            )
                        } else {
                            Text(
                                text = stringResource(id = R.string.sign_up),
                                color = Color.White,
                                modifier = Modifier.padding(horizontal = 32.dp)
                            )
                        }

                    }


                }
            }
            Spacer(modifier = Modifier.size(16.dp))
            Row(horizontalArrangement = Arrangement.Center) {
                Text(
                    text = "${stringResource(id = R.string.already_have_an_account)}?",
                    color = Colors.Quote,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = stringResource(id = R.string.login),
                    modifier = Modifier
                        .clickable {
                            viewModel.onLoginClicked()
                        },
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    color = Colors.Primary,
                    fontWeight = FontWeight.Medium
                )
            }
            GroupSocialButtons(color = Colors.Quote, onFacebookClick = {}, onGoogleClick = {})
        }
    }
}

@Preview
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(rememberNavController())
}