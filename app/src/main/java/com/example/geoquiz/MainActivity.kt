package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
//import com.bignerdranch.android.geoquiz.databinding.ActivityMainBinding
import com.example.geoquiz.databinding.ActivityMainBinding

//Main class
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    // Challenge - Counters to keep track of number of questions answered and how many correct
    private var answeredQuestions = 0
    private var correctAnswers = 0
    // Array to save the state of which questions were answered the size of questionbank variable
//    private var whichAnswered = BooleanArray(quizViewModel.questionBank.size)

    // KEYS to save state if user rotates device and the Activity is recreated
//    private val KEY_ANSWERED_QUESTIONS = "number_answered"
//    private val KEY_CORRECT_ANSWERS = "number_correct"
//    private val KEY_WHICH_QUESTIONS_ANSWERED = "which_answered"

//    Toast function displays the message correct and incorrect message inside the app
//    <- this particular one could be solved differently - I will think of something more elegant later
    private fun toast(messageResID: Int) {
        Toast.makeText(this, messageResID, Toast.LENGTH_SHORT).show()
    }
//    It takes one argument which defines the correct answer
    private fun checkAnswer(userPressedTrue: Boolean) {
//        This value check the index of current question in the bank and lets us know the answer which is on of class arguements
        val answerIstrue = quizViewModel.currentQuestionAnswer
        // Challenge - Mark question as answered and disable buttons and count answered questions
        quizViewModel.questionBank[quizViewModel.currentIndex].alreadyAnswered = true
        buttonsEnabled(false)
        answeredQuestions++

        var messageResId = 0
//        Condtional verifies if the true button was pressed and was it true by accesing class argument
        if (userPressedTrue == answerIstrue) {
//            Further function is creating proper info to display and if true adds it to ealier described correct answers
            messageResId = R.string.correct_toast
            correctAnswers++
        } else {
            messageResId = R.string.incorrect_toast
        }
//        At the end we are getting the toast message depends of our answer and the score calculation
        toast(messageResId)
        calculateScore()
    }
//    here function saves question as imported question string
    private fun updateQuestion() {
        val question = quizViewModel.currentQuestionText
//        setting up text to display
        binding.questionTextView.setText(question)

        // Challenge - disable or enable answer buttons depending if the Question was already answered
        if (!quizViewModel.questionBank[quizViewModel.currentIndex].alreadyAnswered) {
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
        val totalQuestions = quizViewModel.questionBank.size
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
            for (question in quizViewModel.questionBank) {
                question.alreadyAnswered = false
            }

        }
    }

    private lateinit var binding: ActivityMainBinding

    private val quizViewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        binding.trueButton.setOnClickListener {
            checkAnswer(true)

        }

        binding.falseButton.setOnClickListener {
            checkAnswer(false)
        }

        binding.nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        binding.previousButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        updateQuestion()

        binding.questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
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


