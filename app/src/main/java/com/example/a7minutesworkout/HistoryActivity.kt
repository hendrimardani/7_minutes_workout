package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minutesworkout.Databases.HistoryApp
import com.example.a7minutesworkout.Databases.HistoryDao
import com.example.a7minutesworkout.Databases.HistoryEntity
import com.example.a7minutesworkout.HistoryAdapter.HistoryAdapter
import com.example.a7minutesworkout.databinding.ActivityHistoryBinding
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
    }

    private fun setToolBar() {
        // Call object actionBar
        setSupportActionBar(binding.toolbarHistory)
        // Back to home
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = "HISTORY"
        }
        binding.toolbarHistory.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun getAllComptetedDates(historyDao: HistoryDao) {
        lifecycleScope.launch {
            historyDao.fetchAllDates().collect {
                val list = ArrayList(it)
                setupListOfHistory(list, historyDao)
            }
        }
    }

    private fun setupListOfHistory(historyEntity: ArrayList<HistoryEntity>, historyDao: HistoryDao) {
        lifecycleScope.launch {
            historyDao.fetchAllDates().collect {
                if (it.isNotEmpty()) {
                    val historyAdapter = HistoryAdapter(historyEntity
                    ) { deleteDate ->
                        deleteDate(deleteDate, historyDao)
                    }
                    binding.rvHistory.layoutManager = LinearLayoutManager(
                        this@HistoryActivity, LinearLayoutManager.VERTICAL, false)
                    binding.rvHistory.adapter = historyAdapter

                    binding.tvExercise.visibility = View.VISIBLE
                    binding.rvHistory.visibility = View.VISIBLE
                    binding.tvNodata.visibility = View.INVISIBLE
                } else {
                    binding.tvExercise.visibility = View.INVISIBLE
                    binding.rvHistory.visibility = View.INVISIBLE
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
}