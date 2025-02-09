package com.witt.fetchapplication.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.witt.fetchapplication.network.data.Item
import com.witt.fetchapplication.ui.theme.FetchApplicationTheme
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemListView(listScreenState: State<ItemListState>, onListSelected: (Int) -> Unit, modifier: Modifier = Modifier) {
    val listStateValue = listScreenState.value
    Column (modifier) {
        if (listStateValue.isLoading) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
            )
        } else {
            Text("Lists")
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 50.dp)
            ) {
                items(listStateValue.listKeys) { listKey ->
                    OutlinedCard(
                        colors = CardDefaults.cardColors(
                            containerColor = if (listKey == listStateValue.selectedListKey) {
                                MaterialTheme.colorScheme.inverseSurface
                            } else {
                                MaterialTheme.colorScheme.surface
                            },
                        ),
                        border = BorderStroke(1.dp, Color.Black),
                        modifier = Modifier
                            .size(width = 240.dp, height = 50.dp).padding(horizontal = 5.dp),
                        onClick = { onListSelected(listKey) }
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = listKey.toString())
                        }
                    }
                }
            }
            LazyColumn {
                stickyHeader {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.secondary)
                    ) {
                        Text(color = MaterialTheme.colorScheme.onSecondary, text = "List Contents - ${listStateValue.selectedList.size} items", textAlign = TextAlign.Center)
                    }
                }
                items(listStateValue.selectedList) {
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = it.name!!)
                        }
                    }
                }
            }
            if (listStateValue.selectedListKey == -1) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Select a list ID above!")
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ItemListPreview() {
    val sampleMap = mapOf(Pair(1, listOf(
        Item(1, 1, "Item 1"),
        Item(2, 1, "Item 2"),
        Item(3, 1, "Item 3")
    )),
    Pair(2, listOf(
        Item(1, 2, "Item 1")
    )))
    val listState = MutableStateFlow(ItemListState(false, sampleMap.keys.toList(), sampleMap[1]!!, 1)).collectAsState()
    FetchApplicationTheme {
        ItemListView(listState, {})
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun InitialItemListPreview() {
    val sampleMap = mapOf(Pair(1, listOf(
        Item(1, 1, "Item 1"),
        Item(2, 1, "Item 2"),
        Item(3, 1, "Item 3")
    )),
        Pair(2, listOf(
            Item(1, 2, "Item 1")
        )))
    val listState = MutableStateFlow(ItemListState(false, sampleMap.keys.toList(), emptyList(), -1)).collectAsState()
    FetchApplicationTheme {
        ItemListView(listState, {})
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoadingItemListPreview() {
    val listState = MutableStateFlow(ItemListState(true, emptyList(), emptyList(), -1)).collectAsState()
    FetchApplicationTheme {
        ItemListView(listState, {})
    }
}