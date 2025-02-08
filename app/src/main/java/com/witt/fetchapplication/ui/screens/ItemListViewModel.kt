package com.witt.fetchapplication.ui.screens

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.witt.fetchapplication.network.ListApi
import com.witt.fetchapplication.network.data.Item
import kotlinx.coroutines.launch

class ItemListViewModel : ViewModel() {
    private val _itemMap = mutableStateMapOf<Int, List<Item>>()
    val itemMap: Map<Int, List<Item>>
        get() = _itemMap

    fun getItemList() {
        viewModelScope.launch {
            val listResult = ListApi.retrofitService.getItemList().filter { item -> item.name != null && item.name != "" }
            val groupedList = listResult.groupBy { it.listId }.mapValues { list -> list.value.sortedBy { it.name } }
            _itemMap.clear()
            _itemMap.putAll(groupedList)
        }
    }
}