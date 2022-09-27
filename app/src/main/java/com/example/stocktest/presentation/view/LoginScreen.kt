package com.example.stocktest.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stocktest.presentation.viewmodel.LoginViewModel
import com.example.stocktest.ui.theme.TextFieldTextColor
import timber.log.Timber

@Composable
fun LoginScreen(viewModel: LoginViewModel = viewModel()) {

    Timber.tag(MainActivity.TAG).d("LoginScreen()")

    var name by remember { mutableStateOf("C500275") }
    var pw by remember { mutableStateOf("a123456") }
    val context = LocalContext.current

    Surface(modifier = Modifier.fillMaxSize(),color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(value = name,
                onValueChange = { newInput -> name = newInput },
                label = { Text(text = "ID",color = MaterialTheme.colors.TextFieldTextColor) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .padding(top = 25.dp)
            )

            TextField(value = pw,
                onValueChange = { newInput -> pw = newInput },
                label = { Text(text = "Password",color = MaterialTheme.colors.TextFieldTextColor) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .padding(top = 25.dp)
            )

            Button(onClick = {
                if(name.isBlank() || pw.isBlank()) {
                    showToast(context, "아이디나 비밀번호를 확인해주세요.")
                } else {
                    viewModel.signIn(name, pw)
                }
            },modifier = Modifier
                .padding(top = 25.dp)
                .requiredWidth(277.dp)){
                Text(text = "Sign In")
            }
        }
    }
}