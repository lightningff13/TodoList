package com.personal.todolist.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.personal.todolist.ui.ui.theme.TodoListTheme
import com.personal.todolist.ui.viewModels.GetTodoListsViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.foundation.lazy.items

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoListTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TodoLists()
                }
            }
        }
    }
}

@Composable
fun TodoLists(viewModel: GetTodoListsViewModel = hiltViewModel()) {
    val todoListUiState by viewModel.todoListUiState.collectAsStateWithLifecycle()
    when (val currentUiState = todoListUiState) {
        is TodoListState.Error -> TODO()
        TodoListState.Loading -> Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }
        is TodoListState.Success ->
            if(currentUiState.todoLists.isEmpty()){
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("No todolists added yet")
                }
            }
            else{
                LazyColumn {
                    items(items = currentUiState.todoLists) {
                        Text(it.title)
                    }
                }
            }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoListsPreview() {
    TodoListTheme {
        TodoLists()
    }
}