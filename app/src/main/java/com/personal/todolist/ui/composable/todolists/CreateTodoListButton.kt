package com.personal.todolist.ui.composable.todolists

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.personal.todolist.ui.ui.theme.Shampoo
import com.personal.todolist.ui.ui.theme.TodoListTheme
import com.personal.todolist.ui.ui.theme.UnevenBorderShapes

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CreateTodoListButton(
    onCreateClick: (String) -> Unit = {},
    onCalculateFabHeight: (Int) -> Unit = {}
) {
    val initialTextFieldValue = ""
    val keyboardController = LocalSoftwareKeyboardController.current
    var textState by remember { mutableStateOf(TextFieldValue(text = initialTextFieldValue)) }
    val addButtonEnabled by remember {
        derivedStateOf {
            textState.text.isNotEmpty()
        }
    }
    var fabHeight by remember {
        mutableStateOf(0)
    }

    Surface(
        modifier = Modifier.onGloballyPositioned {
            fabHeight = it.size.height
            onCalculateFabHeight(fabHeight)
        },
        shape = UnevenBorderShapes.small,
        color = MaterialTheme.colors.secondary,
        elevation = 10.dp
    ) {
        Row(
            modifier = Modifier.padding(vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = textState,
                onValueChange = { textState = it },
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
                onClick = {
                    onCreateClick(textState.text)
                    textState = TextFieldValue(text = initialTextFieldValue)
                    keyboardController?.hide()
                },
                enabled = addButtonEnabled
            ) {
                Icon(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = "Delete",
                    tint = Color.White.copy(alpha = LocalContentAlpha.current)
                )
            }
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AddTodoListButtonPreview() {
    TodoListTheme {
        CreateTodoListButton()
    }
}