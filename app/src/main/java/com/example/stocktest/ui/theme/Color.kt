package com.example.stocktest.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val LightBlackishGray = Color(0xFF6E707C)
val Colors.TextFieldTextColor : Color
    @Composable
    get() = LightBlackishGray

val DarkBlackishGray = Color(0xFF444653)
val Colors.TextFieldColor : Color
    @Composable
    get() = DarkBlackishGray