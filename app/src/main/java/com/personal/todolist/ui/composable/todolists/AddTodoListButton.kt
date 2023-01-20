package com.personal.todolist.ui.composable.todolists

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.personal.todolist.ui.ui.theme.Shampoo
import com.personal.todolist.ui.ui.theme.TodoListTheme
import com.personal.todolist.ui.ui.theme.UnevenBorderShapes

@Composable
fun AddTodoListButton() {
    Surface(
        shape = UnevenBorderShapes.small,
        color = MaterialTheme.colors.secondary,
        elevation = 10.dp
    ) {
        Row(
            modifier = Modifier.padding(vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val textState = remember { mutableStateOf(TextFieldValue()) }
            TextField(
                value = textState.value,
                onValueChange = { textState.value = it },
                textStyle = MaterialTheme.typography.button,
                placeholder = { Text(text = "Create a note...", color = Shampoo) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.LightGray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Delete",
                    tint = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
fun AddTodoListButtonPreview() {
    TodoListTheme {
        AddTodoListButton()
    }
}