package com.example.expense
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpenseAdapter(
    private val expenseList: List<Expense>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>() {

    interface OnItemClickListener {
        fun onItemLongClick(expense: Expense)
    }

    inner class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.textViewTitle)
        val amountText: TextView = itemView.findViewById(R.id.textViewAmount)

        init {
            itemView.setOnLongClickListener {
                listener.onItemLongClick(expenseList[adapterPosition])
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_expense, parent, false)
        return ExpenseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        val expense = expenseList[position]
        holder.titleText.text = expense.title
        holder.amountText.text = "₹ ${expense.amount}"
    }

    override fun getItemCount(): Int = expenseList.size
}