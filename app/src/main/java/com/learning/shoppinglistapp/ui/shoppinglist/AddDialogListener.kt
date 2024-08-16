package com.learning.shoppinglistapp.ui.shoppinglist

import com.learning.shoppinglistapp.data.db.entites.ShoppingItem

interface AddDialogListener {
    fun onAddButtonClick(item: ShoppingItem)
}