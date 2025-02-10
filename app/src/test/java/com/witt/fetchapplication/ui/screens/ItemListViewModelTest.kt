package com.witt.fetchapplication.ui.screens

import com.witt.fetchapplication.network.data.Item
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ItemListViewModelTest {
    private lateinit var viewModel: ItemListViewModel

    private var mockItemListMap: List<Item> =
        listOf(
            Item(150, 1, "Item 150"),
            Item(15, 1, "Item 15"),
            Item(1, 1, "Item 1"),
            Item(10, 1, "Item 10"),
            Item(3, 1, "Item 3"),
            Item(2, 1, ""),
            Item(4, 1, null),
            Item(15, 2, "Item 15"),
        )

    private var expectedMap: Map<Int, List<Item>> = mapOf(
        Pair(
            1,
            listOf(
                Item(1, 1, "Item 1"),
                Item(3, 1, "Item 3"),
                Item(10, 1, "Item 10"),
                Item(15, 1, "Item 15"),
                Item(150, 1, "Item 150"),
            )
        ),
        Pair(
            2,
            listOf(
                Item(15, 2, "Item 15"),
            )
        )
    )

    @Before
    fun setup() {
        viewModel = ItemListViewModel()
    }

    @Test
    fun `expect formatItemList to return sorted map of sorted item lists when there is a response from the API`() =
        runTest {
            assertEquals(expectedMap, viewModel.formatItemList(mockItemListMap))
        }

    @Test
    fun `expect formatItemList to return an empty map when the API responds with an empty list`() =
        runTest {
            assertEquals(emptyMap<Int, List<Item>>(), viewModel.formatItemList(emptyList()))
        }
}