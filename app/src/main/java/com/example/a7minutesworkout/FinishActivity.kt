package com.example.a7minutesworkout

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import com.example.a7minutesworkout.Databases.HistoryApp
import com.example.a7minutesworkout.Databases.HistoryDao
import com.example.a7minutesworkout.Databases.HistoryEntity
import com.example.a7minutesworkout.databinding.ActivityFinishBinding
import kotlinx.coroutines.launch
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FinishActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFinishBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolBar()
        binding.toolBarFinish.setNavigationOnClickListener {
            onBackPressed()
        }

        binding.btnFinish.setOnClickListener {
            finish()
        }

        val dao = (application as HistoryApp).db.historyDao()
        addDateToDatabase(dao)
        MotionToast.createToast(this@FinishActivity,
            "HISTORI TELAH DITAKMBAHKAN ☹️",
            "Silahkan cek di histori !!!",
            MotionToastStyle.SUCCESS,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(this, R.font.turret_road_bold))
    }

    private fun setToolBar() {
        // Call object actionBar
        setSupportActionBar(binding.toolBarFinish)
        // Back to home
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = "7 Menit Olahraga"
            // Change font style text
            binding.toolBarFinish.setTitleTextAppearance(this@FinishActivity, R.style.font_tangerine_bold)
        }
    }
    private fun addDateToDatabase(historyDao: HistoryDao) {
        val c = Calendar.getInstance()
        val dateTime = c.time
        Log.e("DATE RESULT: ", "$dateTime")

        // 10-03-2024 Min 14:59:11
        val sdf = SimpleDateFormat("dd-MM-yyyy EEE HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime)
        Log.e("FORMATTED DATE RESULT: ", "$date")

        lifecycleScope.launch {
            historyDao.insert(HistoryEntity(date))
            Log.e("DATE RESULT: ", "Added.....")
        }
    }
}