package com.example.weatherapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(navController: NavHostController) {
    val signInThemeColor = colorResource(id = R.color.app_theme_color)

    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.sign_in),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        color = signInThemeColor
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("welcome") }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                            tint = signInThemeColor
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(bottom = 50.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                Icon(
                    imageVector = Icons.Rounded.AccountCircle,
                    contentDescription = stringResource(R.string.account_icon_desc),
                    tint = signInThemeColor,
                    modifier = Modifier.size(100.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                SignInTextField(value = username, onValueChange = { username = it }, label = stringResource(R.string.username), color = signInThemeColor)
                Spacer(modifier = Modifier.height(15.dp))
                SignInTextField(value = password, onValueChange = { password = it }, label = stringResource(R.string.password), isPassword = true, color = signInThemeColor)

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = { navController.navigate("home") },
                    colors = ButtonDefaults.buttonColors(containerColor = signInThemeColor),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                ) {
                    Text(text = stringResource(R.string.sign_in), color = colorResource(id = R.color.white))
                }
            }
        }
    }
}

@Composable
fun SignInTextField(value: String, onValueChange: (String) -> Unit, label: String, isPassword: Boolean = false, color: Color) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = color) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = if (isPassword) KeyboardOptions(keyboardType = KeyboardType.Password) else KeyboardOptions.Default,
        colors = OutlinedTextFieldDefaults.colors(
            cursorColor = color,
            focusedBorderColor = color,
            unfocusedBorderColor = color,
            focusedLabelColor = color,
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewSignInScreen() {
    MaterialTheme {
        SignInScreen(navController = rememberNavController())
    }
}
