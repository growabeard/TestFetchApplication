package com.witt.fetchapplication.ui.screens

import androidx.annotation.OpenForTesting
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
            val listResult = ListApi.retrofitService.getItemList()
            _itemMap.clear()
            _itemMap.putAll(formatItemList(listResult))
        }
    }

    @OpenForTesting
    fun formatItemList(rawList: List<Item>): Map<Int, List<Item>> {
        val listWithNames = rawList.filter { item -> item.name != null && item.name != "" }
        val groupedList = listWithNames.groupBy { it.listId }
        return groupedList.mapValues { list ->
            list.value.sortedWith(compareBy {
                it.name?.filter { char ->
                    char.isDigit()
                }?.toInt()
            })
        }
    }
}