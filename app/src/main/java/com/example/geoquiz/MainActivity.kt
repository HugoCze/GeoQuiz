package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
//import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding
import com.example.geoquiz.databinding.ActivityMainBinding

//Main class
class MainActivity : AppCompatActivity() {

//    List of quesions to display in the app based on Question data class
    private val questionBank = listOf(
        Question(R.string.question_africa, false, false),
        Question(R.string.question_australia, true,false),
        Question(R.string.question_oceans, true, false),
        Question(R.string.question_mideast, false, false),
        Question(R.string.question_americas, true, false),
        Question(R.string.question_asia, true, false),
    )

    private val TAG = "MainActivity"
//    Index variable to operate with
    private var currentIndex = 0
    // Challenge - Counters to keep track of number of questions answered and how many correct
    private var answeredQuestions = 0
    private var correctAnswers = 0
    // Array to save the state of which questions were answered the size of questionbank variable
    private var whichAnswered = BooleanArray(questionBank.size)

    // KEYS to save state if user rotates device and the Activity is recreated
    private val KEY_ANSWERED_QUESTIONS = "number_answered"
    private val KEY_CORRECT_ANSWERS = "number_correct"
    private val KEY_WHICH_QUESTIONS_ANSWERED = "which_answered"

    private fun toast(messageResID: Int) {
        Toast.makeText(this, messageResID, Toast.LENGTH_SHORT).show()
    }

    private fun checkAnswer(userPressedTrue: Boolean) {
        val answerIstrue = questionBank[currentIndex].answer

        // Challenge - Mark question as answered and disable buttons and count answered questions
        questionBank[currentIndex].alreadyAnswered = true
        buttonsEnabled(false)
        answeredQuestions++

        var messageResId = 0

        if (userPressedTrue == answerIstrue) {
            messageResId = R.string.correct_toast
            correctAnswers++
        } else {
            messageResId = R.string.incorrect_toast
        }

        toast(messageResId)
        calculateScore()
    }

    private fun updateQuestion() {
        val question = questionBank[currentIndex].textResId
        binding.questionTextView.setText(question)

        // Challenge - disable or enable answer buttons depending if the Question was already answered
        if (!questionBank[currentIndex].alreadyAnswered) {
            buttonsEnabled(true)
        } else buttonsEnabled(false)
    }

    // Challenge utility method for enabling or disabling buttons
    private fun buttonsEnabled(enabled: Boolean) {
        binding.trueButton.isEnabled = enabled
        binding.falseButton.isEnabled = enabled
    }

    // Challenge method to calculate score
    private fun calculateScore() {
        val totalQuestions = questionBank.size
        val score = correctAnswers * 100 / totalQuestions

        // Challenge - Only shows score if we answered all the questions, and then rests all
        if (answeredQuestions == totalQuestions) {
            val message = "You scored $score% correct answers. Resetting all"
            val toast = Toast.makeText(this, message, Toast.LENGTH_LONG)
            toast.setGravity(Gravity.TOP, 0, 0)
            toast.show()
            // reset
            correctAnswers = 0
            answeredQuestions = 0
            for (question in questionBank) {
                question.alreadyAnswered = false
            }

        }
    }



    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.trueButton.setOnClickListener {
            checkAnswer(true)

        }

        binding.falseButton.setOnClickListener {
            checkAnswer(false)
        }

        binding.nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        binding.previousButton.setOnClickListener {
            currentIndex = (currentIndex - 1) % questionBank.size
            updateQuestion()
        }

        updateQuestion()

        binding.questionTextView.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }




}

