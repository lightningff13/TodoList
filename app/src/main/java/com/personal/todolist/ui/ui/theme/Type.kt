package com.personal.todolist.ui.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.personal.todolist.R

val avenirFamily = FontFamily(
    Font(R.font.avenir_light, FontWeight.Light),
    Font(R.font.avenir_regular, FontWeight.Normal),
    Font(R.font.avenir_medium, FontWeight.Medium),
    Font(R.font.avenir_heavy, FontWeight.SemiBold),
    Font(R.font.avenir_black, FontWeight.Bold),
)

// Set of Material typography styles to start with
val Typography = Typography(
    h1 = TextStyle(
        fontFamily = avenirFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    body1 = TextStyle(
        fontFamily = avenirFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    ),
    button = TextStyle(
        fontFamily = avenirFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
)