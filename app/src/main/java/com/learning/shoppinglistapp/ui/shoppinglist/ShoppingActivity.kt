package com.learning.shoppinglistapp.ui.shoppinglist

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.learning.shoppinglistapp.R
import com.learning.shoppinglistapp.data.db.ShoppingDatabase
import com.learning.shoppinglistapp.data.db.entites.ShoppingItem
import com.learning.shoppinglistapp.data.repositories.ShoppingRepository
import com.learning.shoppinglistapp.other.ShoppingItemAdapter
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein
import org.kodein.di.generic.instance

class ShoppingActivity : AppCompatActivity(), KodeinAware {
    override val kodein by closestKodein()

    private val factory: ShoppingViewModelFactory by instance()
    private lateinit var viewModel: ShoppingViewModel
    private lateinit var adapter: ShoppingItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping)

        // Initialize and set up Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "ShoppingList"
            toolbar.setTitleTextColor(Color.WHITE)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // Optional: show the Up button

        // Initialize database, repository, and ViewModel

        viewModel = ViewModelProvider(this, factory).get(ShoppingViewModel::class.java)

        // Initialize RecyclerView and Adapter
        adapter = ShoppingItemAdapter(listOf(), viewModel)
        val rvShoppingItem: RecyclerView = findViewById(R.id.rvShoppingItem)
        rvShoppingItem.layoutManager = LinearLayoutManager(this)
        rvShoppingItem.adapter = adapter

        // Observe changes in the shopping items
        viewModel.getAllShoppingItems().observe(this, Observer { items ->
            adapter.items = items
            adapter.notifyDataSetChanged()
        })

        // Set up FloatingActionButton click listener
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener {
            val dialog = AddShoppingItemDialog.newInstance()
            dialog.setAddDialogListener(object : AddDialogListener {
                override fun onAddButtonClick(item: ShoppingItem) {
                    viewModel.upsert(item)
                }
            })
            dialog.show(supportFragmentManager, AddShoppingItemDialog.TAG)
        }
    }
}
