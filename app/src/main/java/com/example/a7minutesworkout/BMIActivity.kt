package com.example.a7minutesworkout

import android.annotation.SuppressLint
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.get
import com.example.a7minutesworkout.databinding.ActivityBmiBinding
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.math.BigDecimal
import java.math.RoundingMode

@Suppress("UNUSED_EXPRESSION")
class BMIActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBmiBinding
    private var currentView = METRIC_UNITS_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolBar()
        binding.btnCalculate.setOnClickListener {
            displayResult()
            calculateUnits()
        }

        binding.rgBtn.setOnCheckedChangeListener { radioGroup, i ->
            if (i == R.id.rb_metric_unit) {
                makeVisibleMetricUnitView()
            } else {
                makeVisibleUsUnitView()
            }
        }
    }

    private fun setToolBar() {
        // Call object actionBar
        setSupportActionBar(binding.toolbarBMI)
        // Back to home
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = "7 Menit Olahraga"
            // Change font style text
            binding.toolbarBMI.setTitleTextAppearance(this@BMIActivity, R.style.font_tangerine_bold)
        }
        binding.toolbarBMI.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun makeVisibleMetricUnitView() {
        currentView = METRIC_UNITS_VIEW
        binding.tilMetricUnitWeight.visibility = View.VISIBLE
        binding.tilMetricUnitHeight.visibility = View.VISIBLE

        binding.tilUsUnitWeight.visibility = View.INVISIBLE
        binding.llDisplayLayout2.visibility = View.INVISIBLE

        binding.llDisplayLayout.visibility = View.INVISIBLE

    }

    private fun makeVisibleUsUnitView() {
        currentView = US_UNITS_VIEW
        binding.tilMetricUnitWeight.visibility = View.INVISIBLE
        binding.tilMetricUnitHeight.visibility = View.INVISIBLE
        binding.tilUsUnitWeight.visibility = View.VISIBLE
        binding.llDisplayLayout2.visibility = View.VISIBLE

        binding.llDisplayLayout.visibility = View.INVISIBLE
    }

    private fun validateMetricUnits(): Boolean {
        var isValid = true

        if (binding.etMetricUnitWeight.text.toString().isEmpty()) {
            isValid = false
        } else if (binding.etMetricUnitHeight.text.toString().isEmpty()) {
            isValid = false
        }
        return isValid
    }

    private fun validateUsUnits(): Boolean {
        var isValid = true

        if (binding.etUsUnitInch.text.toString().isEmpty()) {
            isValid = false
        } else if (binding.etUsUnitFeet.text.toString().isEmpty()) {
            isValid = false
        } else if (binding.etUsUnitWeight.text.toString().isEmpty()) {
            isValid = true
        }
        return isValid
    }

    @SuppressLint("ResourceType")
    private fun calculateUnits() {
        if (currentView == METRIC_UNITS_VIEW) {
            if (validateMetricUnits()) {
                val heightValue = binding.etMetricUnitHeight.text.toString().toFloat() / 100
                val weightValue = binding.etMetricUnitWeight.text.toString().toFloat()
                val bmi = weightValue / (heightValue * heightValue)

                displayBMIResult(bmi)

            } else {
                MotionToast.createToast(this@BMIActivity,
                    "GAGAL ☹️",
                    "Kolom tidak boleh kosong !!!",
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.SHORT_DURATION,
                    ResourcesCompat.getFont(this, R.font.turret_road_bold))
            }
        } else {
            if (validateUsUnits()) {
                val weightValue = binding.etUsUnitWeight.text.toString().toFloat()
                val feetValue = binding.etUsUnitFeet.text.toString()
                val inchValue = binding.etUsUnitInch.text.toString()
                val heightValue = inchValue.toFloat() + feetValue.toFloat() * 12

                val bmi = 703 * (weightValue / (heightValue * heightValue))

                displayBMIResult(bmi)

            } else {
                MotionToast.createToast(this@BMIActivity,
                    "GAGAL ☹️",
                    "Kolom tidak boleh kosong !!!",
                    MotionToastStyle.ERROR,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.SHORT_DURATION,
                    ResourcesCompat.getFont(this, R.font.turret_road_bold))
            }
        }
    }

    private fun displayBMIResult(bmi: Float) {
        """"
        Using the compareTo() function to compare Kotlin strings
        You can also compare strings in Kotlin with compareTo(). Here is the basic syntax for this method:
        
        mainStr.compareTo(otherStr)
        While the previous methods return a boolean value (true or false), compareTo() returns an integer:
        
        Returns 0 if the main string and the other string are equal
        Returns a negative number if the other string’s ASCII value is bigger than the main string
        Returns a positive number if the other string’s ASCII value is smaller than the main string
         """
        val bmiTitle: String
        val bmiDescription: String

        if (bmi.compareTo(15f) <= 0) {
            bmiTitle = "Berat badan sangat kurang"
            bmiDescription = "Ups! Anda benar-benar harus menjaga diri Anda dengan lebih baik! Makan lebih banyak lagi!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
            bmiTitle = "Berat badan kurang"
            bmiDescription = "Ups! Anda benar-benar harus menjaga diri Anda dengan lebih baik! Makan lebih banyak lagi!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0) {
            bmiTitle = "Normal"
            bmiDescription = "Selamat! Anda berada dalam kondisi yang baik!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0) {
            bmiTitle = "Kegemukan"
            bmiDescription = "Ups! Anda benar-benar harus menjaga diri sendiri! Solusi latihan"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0) {
            bmiTitle = "Kelas Obesitas | Obesitas sedang"
            bmiDescription = "Ups! Anda benar-benar harus menjaga diri sendiri! Solusi latihan"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0 ) {
            bmiTitle = "Kelas Obesitas | Obesitas Berat"
            bmiDescription = "Anda berada dalam kondisi yang sangat berbahaya! Bertindaklah sekarang."
        } else {
            bmiTitle = "Kelas Obesitas | Obesitas Sangat Parah"
            bmiDescription = "OMG! Anda berada dalam kondisi yang sangat berbahaya! Bertindaklah sekarang."
        }

        val bmiValue = BigDecimal(bmi.toDouble())
            .setScale(2, RoundingMode.HALF_EVEN).toString()
        // Visible and invisible layout
        binding.llDisplayLayout.visibility = View.VISIBLE
        binding.btnCalculate.visibility = View.VISIBLE

        binding.tvTitle.text = bmiTitle
        binding.tvValues.text = bmiValue
        binding.tvDescription.text = bmiDescription
    }

    private fun displayResult() {
        if (validateMetricUnits()) {
            val heightValue = binding.etMetricUnitHeight.text.toString().toFloat() / 100
            val weightValue = binding.etMetricUnitWeight.text.toString().toFloat()
            val bmi = weightValue / ( heightValue * heightValue)

            displayBMIResult(bmi)

        } else {
            MotionToast.createToast(this@BMIActivity,
                "GAGAL ☹️",
                "Kolom tidak boleh kosong !!!",
                MotionToastStyle.ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.SHORT_DURATION,
                ResourcesCompat.getFont(this, R.font.turret_road_bold))
        }
    }

    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
        private const val US_UNITS_VIEW ="US_UNIT_VIEW"
    }
}