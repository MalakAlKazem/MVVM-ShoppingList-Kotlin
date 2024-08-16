package com.learning.shoppinglistapp.data.repositories

import com.learning.shoppinglistapp.data.db.ShoppingDatabase
import com.learning.shoppinglistapp.data.db.entites.ShoppingItem

class ShoppingRepository(
    private val db:ShoppingDatabase
) {
    suspend fun upsert(item:ShoppingItem)=db.getShoppingDao().upsert(item)

    suspend fun delete(item: ShoppingItem) = db.getShoppingDao().delete(item)

    fun getAllShoppingItems() = db.getShoppingDao().getAllShoppingItems()
}