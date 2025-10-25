package com.example.weatherapp.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.R

@Composable
fun WelcomeScreen(navController: NavHostController) {
    val themeColor = colorResource(id = R.color.app_theme_color)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.LightMode,
                contentDescription = stringResource(R.string.light_mode_icon_desc),
                tint = themeColor,
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.welcome_message),
                color = themeColor,
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(58.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            WelcomeButton(
                text = stringResource(R.string.sign_in),
                backgroundColor = themeColor,
                textColor = Color.White,
                onClick = { navController.navigate("signIn") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            WelcomeButton(
                text = stringResource(R.string.sign_up),
                backgroundColor = Color.White,
                textColor = themeColor,
                borderColor = themeColor,
                isOutlined = true,
                onClick = { navController.navigate("signUp") }
            )
        }
    }
}

@Composable
fun WelcomeButton(
    text: String,
    backgroundColor: Color,
    textColor: Color,
    borderColor: Color = Color.Transparent,
    isOutlined: Boolean = false,
    onClick: () -> Unit
) {
    if (isOutlined) {
        OutlinedButton(
            onClick = onClick,
            colors = ButtonDefaults.outlinedButtonColors(containerColor = backgroundColor),
            border = BorderStroke(1.dp, borderColor),
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(45.dp)
        ) {
            Text(text = text, color = textColor)
        }
    } else {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(45.dp)
        ) {
            Text(text = text, color = textColor)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWelcomeScreen() {
    MaterialTheme {
        WelcomeScreen(navController = rememberNavController())
    }
}
