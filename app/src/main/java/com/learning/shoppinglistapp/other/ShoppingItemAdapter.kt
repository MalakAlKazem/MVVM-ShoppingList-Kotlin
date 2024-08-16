package com.learning.shoppinglistapp.other

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.learning.shoppinglistapp.R
import com.learning.shoppinglistapp.data.db.entites.ShoppingItem
import com.learning.shoppinglistapp.ui.shoppinglist.ShoppingViewModel

class ShoppingItemAdapter(
    var items: List<ShoppingItem>,
    private val viewModel: ShoppingViewModel
) : RecyclerView.Adapter<ShoppingItemAdapter.ShoppingViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shopping_item, parent, false)
        return ShoppingViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        val curShoppingItem = items[position]
        holder.bind(curShoppingItem)

    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ShoppingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvAmount: TextView = itemView.findViewById(R.id.tvAmount)
        private val ivDelete: ImageView  = itemView.findViewById(R.id.ivDelete)
        private val ivPlus: ImageView = itemView.findViewById(R.id.ivPlus)
        private val ivMinus: ImageView = itemView.findViewById(R.id.ivMinus)

        fun bind(shoppingItem: ShoppingItem) {
            tvName.text = shoppingItem.name
            tvAmount.text = shoppingItem.amount.toString() // Ensure amount is converted to
            ivDelete.setOnClickListener {
                viewModel.delete(shoppingItem)
            }

            ivPlus.setOnClickListener {
                shoppingItem.amount++
                viewModel.upsert(shoppingItem)
            }
            ivMinus.setOnClickListener {
                if(shoppingItem.amount>0){
                    shoppingItem.amount--
                    viewModel.upsert(shoppingItem)
                }
            }

        }
    }
}
