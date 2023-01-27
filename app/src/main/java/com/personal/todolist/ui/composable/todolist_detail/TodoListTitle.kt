package com.personal.todolist.ui.composable.todolist_detail

import android.content.res.Configuration
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.personal.todolist.common.createTodoList
import com.personal.todolist.ui.ui.theme.TodoListTheme

@Composable
fun TodoListTitle(
    title: String,
    onTodoListTitleChanged: (title: String) -> Unit = {}
) {
    var textState by remember { mutableStateOf(TextFieldValue(text = title)) }
    TextField(
        value = textState,
        onValueChange = {
            textState = it
            onTodoListTitleChanged(it.text)
        },
        textStyle = MaterialTheme.typography.h1,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TodoListTitlePreview() {
    TodoListTheme {
        TodoListTitle(title = createTodoList().title)
    }
}