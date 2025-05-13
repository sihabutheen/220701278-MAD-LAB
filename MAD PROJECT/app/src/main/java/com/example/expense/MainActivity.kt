package com.example.expense

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.app.AlertDialog


class MainActivity : AppCompatActivity(), ExpenseAdapter.OnItemClickListener {

    private lateinit var expenseAdapter: ExpenseAdapter
    private lateinit var dbHelper: ExpenseDatabaseHelper
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = ExpenseDatabaseHelper(this)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadExpenses()

        findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.btnAddExpense).setOnClickListener {
            val intent = Intent(this, AddExpenseActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadExpenses()
    }

    private fun loadExpenses() {
        val expenseList = dbHelper.getAllExpenses()
        expenseAdapter = ExpenseAdapter(expenseList, this)
        recyclerView.adapter = expenseAdapter
    }

    override fun onItemLongClick(expense: Expense) {
        val options = arrayOf("Edit", "Delete")
        val builder = AlertDialog.Builder(this)
        builder.setItems(options) { dialog, which ->
            when (which) {
                0 -> {
                    // Edit
                    val intent = Intent(this, AddExpenseActivity::class.java)
                    intent.putExtra("id", expense.id)
                    intent.putExtra("title", expense.title)
                    intent.putExtra("amount", expense.amount)
                    startActivity(intent)
                }
                1 -> {
                    // Delete
                    dbHelper.deleteExpense(expense.id)
                    loadExpenses()
                }
            }
        }
        builder.show()
    }
}