package com.example.food_hub.ui.features.auth.signup

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.food_hub.R
import com.example.food_hub.ui.AppTextField
import com.example.food_hub.ui.AuthDivider
import com.example.food_hub.ui.SocialButton
import com.example.food_hub.ui.theme.Colors

@Composable
fun SignUpScreen(viewModel: SignUpViewModel = hiltViewModel()) {
    Box(modifier = Modifier.fillMaxSize()) {

        val fullName = viewModel.fullName.collectAsStateWithLifecycle()
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

        Image(
            modifier = Modifier.matchParentSize(),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            painter = painterResource(id = R.drawable.sign_up_background)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(26.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                Modifier
                    .fillMaxHeight()
                    .weight(1f)
            )
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = stringResource(id = R.string.sign_up), color = Color.Black,
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.size(31.dp))
            Column(
                modifier = Modifier,
            ) {
                AppTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = fullName.value,
                    onValueChange = viewModel::onFullNameChange,
                    label = {
                        Text(
                            text = stringResource(id = R.string.full_name),
                            fontSize = 16.sp,
                            color = Colors.Quote,
                            fontWeight = FontWeight.Medium
                        )
                    })
                Spacer(modifier = Modifier.size(29.dp))
                AppTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = email.value,
                    onValueChange = viewModel::onEmailChange,
                    label = {
                        Text(
                            text = stringResource(id = R.string.e_mail),
                            fontSize = 16.sp,
                            color = Colors.Quote,
                            fontWeight = FontWeight.Medium
                        )
                    })
                Spacer(modifier = Modifier.size(29.dp))
                AppTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = password.value,
                    onValueChange = viewModel::onPasswordChange,
                    visualTransformation = PasswordVisualTransformation(),
                    trailingIcon = {
                        Image(
                            painter = painterResource(id = R.drawable.ic_eye),
                            contentDescription = null,
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(id = R.string.password),
                            fontSize = 16.sp,
                            color = Colors.Quote,
                            fontWeight = FontWeight.Medium
                        )
                    })
            }
            Button(
                onClick = viewModel::onSignUpClick,
                modifier = Modifier
                    .padding(horizontal = 38.dp)
                    .padding(vertical = 33.dp)
                    .height(60.dp)
                    .fillMaxWidth()
                    .shadow(shape = RoundedCornerShape(30.dp), elevation = 10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Colors.Primary)
            ) {
                Box {
                    AnimatedContent(
                        targetState = loading.value,
                        label = "",
                        transitionSpec = {
                            fadeIn(animationSpec = tween(300)) + scaleIn(initialScale = 0.8f) togetherWith fadeOut(
                                animationSpec = tween(300)
                            ) + scaleOut(targetScale = 0.8f)
                        }
                    ) { target ->
                        if (target) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                        } else {
                            Text(text = stringResource(id = R.string.sign_up), color = Color.White)
                        }
                    }
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(id = R.string.already_have_an_account),
                    color = Colors.Quote,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                TextButton(onClick = {}) {
                    Text(
                        text = stringResource(id = R.string.login),
                        color = Colors.Primary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            AuthDivider(
                text = R.string.sign_up_with,
                color = Colors.Quote,
                modifier = Modifier.padding(vertical = 30.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SocialButton(text = R.string.facebook, icon = R.drawable.ic_facebook) { }
                SocialButton(text = R.string.google, icon = R.drawable.ic_google) { }
            }
        }
    }
}

@Preview
@Composable
fun SignUpScreenPreview() {
    SignUpScreen()
}