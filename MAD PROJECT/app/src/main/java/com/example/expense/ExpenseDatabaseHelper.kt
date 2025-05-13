package com.example.expense

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ExpenseDatabaseHelper(context: Context) : SQLiteOpenHelper(context, "expenses.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE expenses (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, amount REAL)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS expenses")
        onCreate(db)
    }

    fun insertExpense(title: String, amount: Double) {
        val db = writableDatabase
        val values = ContentValues()
        values.put("title", title)
        values.put("amount", amount)
        db.insert("expenses", null, values)
        db.close()
    }

    fun getAllExpenses(): List<Expense> {
        val expenses = mutableListOf<Expense>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM expenses", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
                val amount = cursor.getDouble(cursor.getColumnIndexOrThrow("amount"))
                expenses.add(Expense(id, title, amount))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return expenses
    }

    fun deleteExpense(id: Int) {
        val db = writableDatabase
        db.delete("expenses", "id=?", arrayOf(id.toString()))
        db.close()
    }

    fun updateExpense(id: Int, title: String, amount: Double) {
        val db = writableDatabase
        val values = ContentValues()
        values.put("title", title)
        values.put("amount", amount)
        db.update("expenses", values, "id=?", arrayOf(id.toString()))
        db.close()
    }
}
