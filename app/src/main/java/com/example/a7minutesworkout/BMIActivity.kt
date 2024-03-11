package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.a7minutesworkout.databinding.ActivityBmiBinding
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

        binding.btnCalculate2.setOnClickListener {
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
            supportActionBar!!.title = "CALCULATOR"
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

        // Clear the text when doesn't select
        binding.etMetricUnitWeight.text!!.clear()
        binding.etMetricUnitHeight.text!!.clear()

        binding.llDisplayLayout.visibility = View.INVISIBLE
    }

    private fun makeVisibleUsUnitView() {
        currentView = US_UNITS_VIEW
        binding.tilMetricUnitWeight.visibility = View.INVISIBLE
        binding.tilMetricUnitHeight.visibility = View.INVISIBLE
        binding.tilUsUnitWeight.visibility = View.VISIBLE
        binding.llDisplayLayout2.visibility = View.VISIBLE

        // Clear the text when doesn't select
        binding.etUsUnitWeight.text!!.clear()
        binding.etUsUnitFeet.text!!.clear()
        binding.etUsUnitInch.text!!.clear()

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

    private fun calculateUnits() {
        if (currentView == METRIC_UNITS_VIEW) {
            if (validateMetricUnits()) {
                val heightValue = binding.etMetricUnitHeight.text.toString().toFloat() / 100
                val weightValue = binding.etMetricUnitWeight.text.toString().toFloat()
                val bmi = weightValue / (heightValue * heightValue)

                displayBMIResult(bmi)

            } else {
                Toast.makeText(this, "Please enter valid values!", Toast.LENGTH_LONG).show()
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
                Toast.makeText(this, "Please enter valid values!", Toast.LENGTH_LONG).show()
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
            bmiTitle = "Very severely underweight"
            bmiDescription = "Oops! You really need to make better care of yourself! Eat more again !"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0) {
            bmiTitle = "Underweight"
            bmiDescription = "Oops! You really need to make better care of yourself! Eat more again !"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0) {
            bmiTitle = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0) {
            bmiTitle = "Overweight"
            bmiDescription = "Oops! You really need to take care of yourself! Workout solutions"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0) {
            bmiTitle = "Obese Class | Moderately obese"
            bmiDescription = "Oops! You really need to take care of yourself! Workout solutions"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0 ) {
            bmiTitle = "Obese Class | Severely Obese"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now"
        } else {
            bmiTitle = "Obese Class | Very Severely Obese"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now"
        }

        val bmiValue = BigDecimal(bmi.toDouble())
            .setScale(2, RoundingMode.HALF_EVEN).toString()
        // Visible and invisible layout
        binding.llDisplayLayout.visibility = View.VISIBLE
        binding.btnCalculate.visibility = View.INVISIBLE
        binding.btnCalculate2.visibility = View.VISIBLE

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
            Toast.makeText(this, "Please enter valid values", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
        private const val US_UNITS_VIEW ="US_UNIT_VIEW"
    }
}