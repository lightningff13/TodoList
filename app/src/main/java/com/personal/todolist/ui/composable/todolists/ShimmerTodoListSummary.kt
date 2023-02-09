package com.personal.todolist.ui.composable.todolists

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.personal.todolist.ui.composable.common.shimmerEffect
import com.personal.todolist.ui.ui.theme.TodoListTheme

@Composable
fun ShimmerTodoListSummary(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(80.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = 10.dp,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .weight(4.0F)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .shimmerEffect()
                )
            }
            Box(
                modifier = Modifier
                    .weight(1.0F),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.2f)
                        .height(15.dp)
                        .shimmerEffect()
                )
            }
        }
    }
}

@Preview
@Composable
fun ShimmerTodoListSummaryPreview() {
    TodoListTheme {
        ShimmerTodoListSummary()
    }
}
