package com.personal.todolist.ui.composable.todolist_detail

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import com.personal.todolist.common.createTask
import com.personal.todolist.domain.models.Task
import com.personal.todolist.ui.ui.theme.KashmirBlue
import com.personal.todolist.ui.ui.theme.TodoListTheme

@Composable
fun Task(
    task: Task,
    onDescriptionChanged: (String) -> Unit = { },
    onCompleteChanged: (Boolean) -> Unit = { },
    onRemoveTaskClicked: () -> Unit = {},
    fieldsEnabled: Boolean = true
) {
    var taskSelected by remember {
        mutableStateOf(task.complete)
    }
    val initialTextFieldValue = task.description
    var textState by remember { mutableStateOf(TextFieldValue(text = initialTextFieldValue)) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            modifier = Modifier.weight(1.0F),
            selected = taskSelected,
            onClick = {
                taskSelected = !taskSelected
                onCompleteChanged(taskSelected)
            },
            colors = RadioButtonDefaults.colors(
                selectedColor = LocalContentColor.current.copy(
                    if (!fieldsEnabled)
                        ContentAlpha.disabled
                    else
                        LocalContentAlpha.current
                )
            )
        )
        TextField(
            modifier = Modifier.weight(7.0F),
            value = textState,
            onValueChange = {
                textState = it
                onDescriptionChanged(it.text)
            },
            textStyle = MaterialTheme.typography.h3.copy(
                textDecoration =
                if (!fieldsEnabled)
                    TextDecoration.LineThrough
                else
                    TextDecoration.None
            ),
            colors = TextFieldDefaults.textFieldColors(
                textColor = LocalContentColor.current.copy(
                    if (!fieldsEnabled)
                        ContentAlpha.disabled
                    else
                        LocalContentAlpha.current
                ),
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
        IconButton(
            modifier = Modifier.weight(1.0F),
            onClick = onRemoveTaskClicked
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Delete"
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TaskToAdd(
    onValueChange: (String) -> Unit = {}
) {
    val textState by remember { mutableStateOf(TextFieldValue(text = "")) }
    val keyboardController = LocalSoftwareKeyboardController.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            modifier = Modifier.weight(1.0F),
            enabled = false,
            selected = false,
            onClick = null
        )
        TextField(
            modifier = Modifier.weight(8.0F),
            value = textState,
            onValueChange = {
                onValueChange(it.text)
                keyboardController?.hide()
            },
            textStyle = MaterialTheme.typography.h3.copy(color = KashmirBlue),
            placeholder = {
                Text(
                    text = "Add an item...",
                    style = MaterialTheme.typography.h3.copy(color = KashmirBlue)
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TaskUncompletedPreview() {
    TodoListTheme {
        Task(createTask())
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TaskCompletedPreview() {
    TodoListTheme {
        Task(createTask(complete = true), fieldsEnabled = false)
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun NewTaskPreview() {
    TodoListTheme {
        TaskToAdd()
    }
}