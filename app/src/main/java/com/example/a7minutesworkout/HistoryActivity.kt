package com.example.a7minutesworkout

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minutesworkout.Databases.HistoryApp
import com.example.a7minutesworkout.Databases.HistoryDao
import com.example.a7minutesworkout.Databases.HistoryEntity
import com.example.a7minutesworkout.HistoryAdapter.HistoryAdapter
import com.example.a7minutesworkout.databinding.ActivityHistoryBinding
import com.example.a7minutesworkout.databinding.DialogCostumeBackConfirmationBinding
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolBar()

        // Create dao
        val dao  = (application as HistoryApp).db.historyDao()

        // Get item date
        getAllComptetedDates(dao)

        // Button delete all items
        binding.btnAllDelete.setOnClickListener {
            customDialogDeleteAllItem(dao)
        }
    }

    private fun setToolBar() {
        // Call object actionBar
        setSupportActionBar(binding.toolbarHistory)
        supportActionBar!!.title = "7 Menit Olahraga"
        // Change font style text
        binding.toolbarHistory.setTitleTextAppearance(this@HistoryActivity, R.style.font_tangerine_bold)
        // Back to home
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        binding.toolbarHistory.setNavigationOnClickListener {
            onBackPressed()
        }
    }
    
    @SuppressLint("SetTextI18n")
    private fun customDialogDeleteAllItem(historyDao: HistoryDao) {
        val customDialog = Dialog(this@HistoryActivity)
        val dialogBinding = DialogCostumeBackConfirmationBinding
            .inflate(layoutInflater)

        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)
        customDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Set text
        dialogBinding.tvTitle.setTextColor(ContextCompat
            .getColor(this@HistoryActivity, R.color.red))
        dialogBinding.tvDescription.text = "Apakah anda yakin ingin menghapus semua histori ?"

        dialogBinding.tvYes.setOnClickListener {
            // Call deleteAllDates method
            deleteAllDates(historyDao)
            customDialog.dismiss()
        }
        dialogBinding.tvNo.setOnClickListener {
            customDialog.dismiss()
        }
        // Display dialog
        customDialog.show()
    }

    private fun getCountItem(item: String) {
        binding.lhtTvNumber.text = item
    }
    private fun getAllComptetedDates(historyDao: HistoryDao) {
        lifecycleScope.launch {
            historyDao.fetchAllDates().collect {
                val list = ArrayList(it)
                setupListOfHistory(list, historyDao)

                // get item count
                getCountItem(list.size.toString())
            }
        }
    }

    private fun setupListOfHistory(historyEntity: ArrayList<HistoryEntity>, historyDao: HistoryDao) {
        lifecycleScope.launch {
            historyDao.fetchAllDates().collect {
                val countItem = it.size

                if (it.isNotEmpty()) {
                    val historyAdapter = HistoryAdapter(historyEntity
                    ) { deleteDate ->
                        deleteDate(deleteDate, historyDao)
                    }
                    binding.rvHistory.layoutManager = LinearLayoutManager(
                        this@HistoryActivity, LinearLayoutManager.VERTICAL, false)
                    binding.rvHistory.adapter = historyAdapter

                    // When input data automatically to last index
                    binding.rvHistory.layoutManager!!.smoothScrollToPosition(binding.rvHistory, null, countItem - 1)

                    binding.llHistoryText.visibility = View.VISIBLE
                    binding.rvHistory.visibility = View.VISIBLE
                    binding.btnAllDelete.visibility = View.VISIBLE
                    binding.tvNodata.visibility = View.INVISIBLE
                } else {
                    binding.llHistoryText.visibility = View.INVISIBLE
                    binding.rvHistory.visibility = View.INVISIBLE
                    binding.btnAllDelete.visibility = View.INVISIBLE
                    binding.tvNodata.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun deleteDate(date: String, historyDao: HistoryDao) {
        lifecycleScope.launch {
            historyDao.delete(HistoryEntity(date))
            Toast.makeText(this@HistoryActivity, "Histori terhapus", Toast.LENGTH_LONG).show()
        }
    }
    private fun deleteAllDates(historyDao: HistoryDao) {
        lifecycleScope.launch {
            historyDao.deleteAll()
        }
    }
}