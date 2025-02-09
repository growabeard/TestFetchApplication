package com.witt.fetchapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.witt.fetchapplication.ui.screens.ItemListView
import com.witt.fetchapplication.ui.screens.ItemListViewModel
import com.witt.fetchapplication.ui.theme.FetchApplicationTheme

class MainActivity : ComponentActivity() {
    private lateinit var itemListViewModel: ItemListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        itemListViewModel = ItemListViewModel()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LaunchedEffect(Unit, block = {
              itemListViewModel.getItemList()
            })

            FetchApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ItemListView(
                        itemListViewModel.uiState.collectAsState(),
                        itemListViewModel::listSelected,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}