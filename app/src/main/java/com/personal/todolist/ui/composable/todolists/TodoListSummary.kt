package com.personal.todolist.ui.composable.todolists

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.personal.todolist.common.createTask
import com.personal.todolist.domain.models.Task
import com.personal.todolist.ui.ui.theme.TodoListTheme

@Composable
fun TodoListSummary(
    todoListTitle: String,
    taskList: List<Task>,
    onDeleteClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier.padding(10.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = 10.dp
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .weight(4.0F)
            ) {
                Text(
                    text = todoListTitle,
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
                Text(
                    text = taskList.joinToString(", ") { it.description },
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(modifier = Modifier.weight(1.0F), onClick = { onDeleteClick() }) {
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