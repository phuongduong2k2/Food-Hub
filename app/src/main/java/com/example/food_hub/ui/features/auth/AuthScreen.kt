package com.example.food_hub.ui.features.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.food_hub.R
import com.example.food_hub.ui.AuthDivider
import com.example.food_hub.ui.SocialButton
import com.example.food_hub.ui.navigation.Login
import com.example.food_hub.ui.navigation.SignUp
import com.example.food_hub.ui.theme.Colors

@Composable
fun AuthScreen(navController: NavController) {
    val imageSize = remember {
        mutableStateOf(IntSize.Zero)
    }
    val brush = Brush.verticalGradient(
        colors = listOf(
            Color.Transparent,
            Color.Black
        ),
        startY = imageSize.value.height.toFloat() / 3
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .matchParentSize()
                .alpha(0.6f)
        )
        // Vertical Linear Gradient
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(brush = brush)
        )
        Button(
            onClick = {},
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp),
            colors = ButtonDefaults.buttonColors(Color.White)
        ) {
            Text(text = stringResource(id = R.string.skip), color = Colors.Primary)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 110.dp)
                .padding(horizontal = 28.dp)
        ) {
            Text(
                text = stringResource(id = R.string.welcome),
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(id = R.string.food_hub),
                style = TextStyle(
                    fontSize = 50.sp,
                    fontWeight = FontWeight.Bold,
                    color = Colors.Primary
                )
            )
            Text(
                text = stringResource(id = R.string.introduce_quote),
                fontSize = 18.sp,
                color = Colors.Quote
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 10.dp)
                .padding(horizontal = 50.dp)
        ) {
            AuthDivider(text = R.string.sign_in_with)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 18.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SocialButton(
                    title = R.string.facebook,
                    icon = R.drawable.ic_facebook,
                    onClick = {}
                )
                SocialButton(
                    title = R.string.google,
                    icon = R.drawable.ic_google,
                    onClick = {}
                )
            }
            OutlinedButton(
                onClick = {
                    navController.navigate(SignUp)
                },
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .height(54.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.21f)),
                border = BorderStroke(1.dp, Color.White)
            ) {
                Text(
                    text = stringResource(id = R.string.start_with_email_or_phone),
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "${stringResource(id = R.string.already_have_an_account)} ?",
                    color = Color.White,
                    fontSize = 16.sp
                )
                Text(
                    modifier = Modifier
                        .clickable {
                            navController.navigate(Login)
                        }
                        .padding(start = 5.dp),
                    text = stringResource(id = R.string.sign_in),
                    textDecoration = TextDecoration.Underline,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthScreenPreview() {
    AuthScreen(rememberNavController())
}