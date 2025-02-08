package com.witt.fetchapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.witt.fetchapplication.network.data.Item
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
                        itemListViewModel.itemMap,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}