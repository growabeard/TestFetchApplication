package com.witt.fetchapplication.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.witt.fetchapplication.network.data.Item
import com.witt.fetchapplication.ui.theme.FetchApplicationTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemListView(map: Map<Int, List<Item>>, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(horizontal = 10.dp)) {
        LazyColumn {
            stickyHeader {
                Box(modifier = Modifier.fillMaxWidth().background(Color.DarkGray)) {
                    ItemRow("ID", "List ID", "Name")
                }
            }
            map.forEach { (_, valueList) ->
                item {
                    Row {
                        Column {
                            valueList.forEach { value ->
                                ItemRow(
                                    id = value.id.toString(),
                                    listId = value.listId.toString(),
                                    name = value.name!!
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ItemRow(id: String, listId: String, name: String) {
    Row {
        Text (modifier = Modifier.weight(2f),
            text = id)
        Text (modifier = Modifier.weight(1f),
            text = listId)
        Text (modifier = Modifier.weight(3f),
            text = name)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val sampleMap = HashMap<Int, List<Item>>()
    sampleMap[1] = listOf(
        Item(1, 1, "Item 1"),
        Item(2, 1, "Item 2"),
        Item(3, 1, "Item 3")
    )
    FetchApplicationTheme {
        ItemListView(sampleMap)
    }
}