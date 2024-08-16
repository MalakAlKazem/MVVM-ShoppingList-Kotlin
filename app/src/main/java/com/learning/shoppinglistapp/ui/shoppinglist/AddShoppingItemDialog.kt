package com.learning.shoppinglistapp.ui.shoppinglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.learning.shoppinglistapp.R
import com.learning.shoppinglistapp.data.db.entites.ShoppingItem

class AddShoppingItemDialog : DialogFragment() {

    private var addDialogListener: AddDialogListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.dialog_add_shopping_item, container, false)

        val tvAdd: TextView = view.findViewById(R.id.tvAdd)
        val etName: EditText = view.findViewById(R.id.etName)
        val etAmount: EditText = view.findViewById(R.id.etAmount)
        val tvCancel: TextView = view.findViewById(R.id.tvCancel)

        tvAdd.setOnClickListener {
            val name = etName.text.toString()
            val amountStr = etAmount.text.toString()

            if (name.isEmpty() || amountStr.isEmpty()) {
                Toast.makeText(context, "Please enter all the information", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val amount = amountStr.toIntOrNull()
            if (amount == null) {
                Toast.makeText(context, "Amount must be a number", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val item = ShoppingItem(name, amount)
            addDialogListener?.onAddButtonClick(item)
            dismiss()
        }

        tvCancel.setOnClickListener {
            dismiss()
        }

        return view
    }

    fun setAddDialogListener(listener: AddDialogListener) {
        addDialogListener = listener
    }

    companion object {
        const val TAG = "AddShoppingItemDialog"

        fun newInstance(): AddShoppingItemDialog {
            return AddShoppingItemDialog()
        }
    }
}
