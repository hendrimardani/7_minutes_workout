package com.example.a7minutesworkout.HistoryAdapter

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minutesworkout.Databases.HistoryEntity
import com.example.a7minutesworkout.HistoryActivity
import com.example.a7minutesworkout.R
import com.example.a7minutesworkout.databinding.DialogCostumeBackConfirmationBinding
import com.example.a7minutesworkout.databinding.ItemHistoryRowBinding

class HistoryAdapter(
    val item: ArrayList<HistoryEntity>,
    val deleteHistory: (date: String) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemHistoryRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val llHistory = binding.llHistory
        val tvNumber = binding.tvNumber
        val tvDate = binding.tvDate
        val ivDelete = binding.ivDelete
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
                .getColor(holder.itemView.context, R.color.light_gray_200))
            holder.tvDate.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
        } else {
            holder.llHistory.setBackgroundColor(ContextCompat
                .getColor(holder.itemView.context, R.color.white))
        }

        holder.ivDelete.setOnClickListener {
            customDialogDelete(holder, date)
        }
    }
    @SuppressLint("SetTextI18n")
    private fun customDialogDelete(holder: ViewHolder, date: String) {
        val context = holder.itemView.context
        val customDialog = Dialog(context)
        val dialogBinding = DialogCostumeBackConfirmationBinding
            .inflate(LayoutInflater.from(context))

        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        customDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Set text
        dialogBinding.tvDescription.text = "Apakah anda yakin ingin menghapus histori ?"

        dialogBinding.tvYes.setOnClickListener {
            // We will destroy activity
            deleteHistory.invoke(date)
            customDialog.dismiss()
        }
        dialogBinding.tvNo.setOnClickListener {
            customDialog.dismiss()
        }
        // Display dialog
        customDialog.show()
    }
}