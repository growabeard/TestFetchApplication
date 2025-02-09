package com.witt.fetchapplication.ui.screens

import androidx.annotation.OpenForTesting
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.witt.fetchapplication.network.ListApi
import com.witt.fetchapplication.network.data.Item
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ItemListViewModel : ViewModel() {
    private val _itemMap = mutableStateMapOf<Int, List<Item>>()

    private val _uiState = MutableStateFlow(ItemListState())
    val uiState: StateFlow<ItemListState> = _uiState.asStateFlow()

    fun getItemList() {
        viewModelScope.launch {
            _uiState.value = ItemListState(true)
            val listResult = ListApi.retrofitService.getItemList()
            _itemMap.clear()
            _itemMap.putAll(formatItemList(listResult))
            _uiState.value = ItemListState(false, _itemMap.keys.toList())
        }
    }

    fun listSelected(listId: Int) {
        if (_itemMap.containsKey(listId)) {
            _itemMap[listId]?.let { list ->
                _uiState.update {
                    ItemListState(false, _itemMap.keys.toList(), list, listId)
                }
            }
        }
    }

    @OpenForTesting
    fun formatItemList(originalList: List<Item>): Map<Int, List<Item>> {
        val listWithNames = originalList.filter { item -> item.name != null && item.name != "" }
        val groupedList = listWithNames.groupBy { it.listId }
        return groupedList.mapValues { list -> list.value.sortedWith(compareBy {
           it.name?.filter {
               char -> char.isDigit()
           }?.toInt()
        })}
    }
}