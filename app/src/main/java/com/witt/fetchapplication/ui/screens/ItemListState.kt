package com.witt.fetchapplication.ui.screens

import com.witt.fetchapplication.network.data.Item

data class ItemListState(
  val isLoading: Boolean = false,
  val listKeys: List<Int> = emptyList(),
  val selectedList: List<Item> = emptyList(),
  val selectedListKey: Int = -1
)
