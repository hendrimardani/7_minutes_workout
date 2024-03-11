package com.example.a7minutesworkout.HistoryAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minutesworkout.Databases.HistoryEntity
import com.example.a7minutesworkout.R
import com.example.a7minutesworkout.databinding.ItemHistoryRowBinding

class HistoryAdapter(
    val item: ArrayList<HistoryEntity>,
    val deleteHistory: (date: String) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemHistoryRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val llHistory = binding.llHistory
        val tvNumber = binding.tvNumber
        val tvDate = binding.tvDate
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemHistoryRowBinding
            .inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return item.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date = item[position].date
        holder.tvNumber.text = (position + 1).toString()
        holder.tvDate.text = date

        if (position % 2 == 0) {
            holder.llHistory.setBackgroundColor(ContextCompat
                .getColor(holder.itemView.context, R.color.light_gray))
            holder.tvDate.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.light_green))
        } else {
            holder.llHistory.setBackgroundColor(ContextCompat
                .getColor(holder.itemView.context, R.color.white))
        }
    }
}