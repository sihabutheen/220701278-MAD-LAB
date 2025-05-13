package com.example.expense

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class AddExpenseActivity : AppCompatActivity() {

    private lateinit var dbHelper: ExpenseDatabaseHelper
    private var expenseId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_expense)

        dbHelper = ExpenseDatabaseHelper(this)

        val titleEditText = findViewById<EditText>(R.id.editTextTitle)
        val amountEditText = findViewById<EditText>(R.id.editTextAmount)
        val saveButton = findViewById<Button>(R.id.btnSave)

        // Check if coming for update
        expenseId = intent.getIntExtra("id", -1)
        if (expenseId != -1) {
            titleEditText.setText(intent.getStringExtra("title"))
            amountEditText.setText(intent.getStringExtra("amount").toString())
        }

        saveButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val amount = amountEditText.text.toString().toDoubleOrNull() ?: 0.0

            if (expenseId == -1) {
                dbHelper.insertExpense(title, amount)
            } else {
                dbHelper.updateExpense(expenseId, title, amount)
            }
            finish()
        }
    }
}