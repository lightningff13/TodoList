package com.personal.todolist.ui.composable.todolists

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.personal.todolist.common.createTask
import com.personal.todolist.common.models.Task
import com.personal.todolist.ui.ui.theme.TodoListTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TodoListSummary(
    todoListTitle: String,
    taskList: List<Task>,
    onTodoListClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier.padding(10.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = 10.dp,
        onClick = onTodoListClick
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .weight(4.0F)
            ) {
                Text(
                    text = todoListTitle,
                    style = MaterialTheme.typography.h2
                )
                Text(
                    text = taskList.joinToString(", ") { it.description },
                    style = MaterialTheme.typography.body1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(modifier = Modifier.weight(1.0F), onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TodoListSummaryPreview() {
    TodoListTheme {
        TodoListSummary(
            todoListTitle = "Groceries List", taskList = listOf(
                createTask(description = "Carrots"),
                createTask(description = "Curry Spices"),
                createTask(description = "Nutella"),
                createTask(description = "Bread"),
                createTask(description = "Flour"),
            )
        )
    }
}