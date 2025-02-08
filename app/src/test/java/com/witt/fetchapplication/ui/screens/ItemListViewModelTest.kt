package com.witt.fetchapplication.ui.screens

import com.witt.fetchapplication.network.ListApiService
import com.witt.fetchapplication.network.data.Item
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import org.mockito.Mock
import org.mockito.kotlin.whenever

class ItemListViewModelTest {

    @Mock
    lateinit var listApiService: ListApiService

    private lateinit var viewModel: ItemListViewModel

    private var mockItemListMap: List<Item> =
            listOf(
                Item(15, 1, "Item 15"),
                Item(3, 1, "Item 3")
            )

    private var expectedMap: Map<Int, List<Item>> = mapOf(
        Pair(1,
            listOf(
                Item(3, 1, "Item 3"),
                Item(15, 1, "Item 15")
            )
        )
    )

    @Before
    fun setup() {
        viewModel = ItemListViewModel()
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `expect getItemList to return sorted map of sorted item lists when there is a response from the API`() = runTest {
        whenever(listApiService.getItemList()).thenReturn(mockItemListMap)
        viewModel.getItemList()
        assertEquals(expectedMap, viewModel.itemMap)
    }

    @Test
    fun `expect getItemList to return an empty map when the API errors out`() = runTest {
        whenever(listApiService.getItemList()).thenReturn(mockItemListMap)
        viewModel.getItemList()
        assertEquals(expectedMap, viewModel.itemMap)
    }
}