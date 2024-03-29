package com.personal.todolist.ui.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(6.dp),
    large = RoundedCornerShape(8.dp)
)

val UnevenBorderShapes = Shapes(
    small = RoundedCornerShape(10.dp, 10.dp, 50.dp, 50.dp)
)