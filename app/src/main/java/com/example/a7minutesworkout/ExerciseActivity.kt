package com.example.a7minutesworkout

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minutesworkout.databinding.ActivityExerciseBinding
import com.example.a7minutesworkout.databinding.DialogCostumeBackConfirmationBinding
import java.util.Locale

class ExerciseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExerciseBinding
    private lateinit var exerciseAdapter: ExerciseStatusAdapter

    private var restTimer: CountDownTimer? = null
    private var restProgress = 0
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0
    private var exerciseList: ArrayList<ExerciseModel>? = null
    private var currentPosition = 0
    private var tts: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("hasil", "ONCREATE")
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        exerciseList = Constants.defaultArrayList()

        setToolBar()
        setupRestView()
        setupExerciseStatusRecyclerView()
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        // We don't use this, becausee of dialog costume
//        super.onBackPressed()
        customDialogForBackButton()
    }

    private fun setToolBar() {
        // Call object actionBar
        setSupportActionBar(binding.toolBarExercise)
        // Back to home
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        binding.toolBarExercise.setNavigationOnClickListener {
            customDialogForBackButton()
        }
    }

    private fun customDialogForBackButton() {
        val customDialog = Dialog(this)
        val dialogBinding = DialogCostumeBackConfirmationBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)

        dialogBinding.btnYes.setOnClickListener {
            // We will destroy activity
            this@ExerciseActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.btnNo.setOnClickListener {
            customDialog.dismiss()
        }
        // Display dialog
        customDialog.show()
    }

    private fun setupExerciseStatusRecyclerView() {
        binding.rvStatus.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL, false)
        exerciseAdapter = ExerciseStatusAdapter(exerciseList!!)
        binding.rvStatus.adapter = exerciseAdapter
    }

    private fun setupRestView() {
        try {
            val soundURI = Uri.parse("android.resource://com.example.a7minutesworkout/" + R.raw.output_pria)
            val player = MediaPlayer.create(applicationContext, soundURI)
            player.isLooping = false
            player.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Change the property visibility to GONE or INVISIBLE
        binding.flRestView.visibility = View.VISIBLE
        binding.tvTitle.visibility = View.VISIBLE
        binding.tvUpcomingExerciseLabel.visibility = View.VISIBLE
        binding.tvUpcomingExercise.visibility = View.VISIBLE

        binding.tvExercise.visibility = View.INVISIBLE
        binding.flExerciseView.visibility = View.INVISIBLE
        binding.ivExercise.visibility = View.INVISIBLE

        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }
        binding.tvUpcomingExercise.text = exerciseList!![currentPosition].getName()
        setRestProgressBar()
    }

    private fun setupExerciseView() {
        // Change the property visibility to GONE or INVISIBLE
        binding.flRestView.visibility = View.INVISIBLE
        binding.tvTitle.visibility = View.INVISIBLE
        binding.tvUpcomingExerciseLabel.visibility = View.INVISIBLE
        binding.tvUpcomingExercise.visibility = View.INVISIBLE

        binding.tvExercise.visibility = View.VISIBLE
        binding.flExerciseView.visibility = View.VISIBLE
        binding.ivExercise.visibility = View.VISIBLE

        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }

        tts = TextToSpeech(this@ExerciseActivity, TextToSpeech.OnInitListener {
            if (it == TextToSpeech.SUCCESS) {
                speakOut(exerciseList!![currentPosition - 1].getName())
            }
        })

        binding.ivExercise.setImageResource(exerciseList!![currentPosition].getImage())
        binding.tvExercise.text = exerciseList!![currentPosition].getName()

        setExerciseProgressBar()
    }

    private fun setRestProgressBar() {
        binding.progressBar.progress = restProgress
        restTimer = object: CountDownTimer(1_000, 1_000) {
            override fun onTick(millisUntilFinished: Long) {
                restProgress++
                binding.progressBar.progress = 10 - restProgress
                // Change to String !
                binding.tvTimer.text = (10 - restProgress).toString()
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onFinish() {
                exerciseList!![currentPosition].setIsSelected(true)
                exerciseAdapter.notifyDataSetChanged()
                // Intent to Exercise
                setupExerciseView()
                currentPosition++
            }
        }.start()
    }

    private fun setExerciseProgressBar() {
        binding.progressBarExercise.progress = exerciseProgress
        exerciseTimer = object: CountDownTimer(1_000, 1_000) {
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding.progressBarExercise.progress = 30 - exerciseProgress
                // Change to String !
                binding.tvTimerExercise.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {
                if (currentPosition < exerciseList!!.size) {
                    exerciseList!![currentPosition].setIsSelected(false)
                    exerciseList!![currentPosition].setIsCompleted(true)
                    exerciseAdapter.notifyDataSetChanged()
                    setupRestView()
                } else {
                    finish()
                    val intent = Intent(this@ExerciseActivity, FinishActivity::class.java)
                    startActivity(intent)
                }
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("hasil", "ONCDESTROY")
        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
        }

        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
    }
    private fun speakOut(text: String) {
        tts!!.language = Locale.US
        tts!!.setSpeechRate(1.0f)
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null)
    }
}