package com.example.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel: ViewModel() {

//    init{
//        Log.d(TAG, "ViewModel instance created")
//    }

    //    List of quesions to display in the app based on Question data class
    var questionBank = listOf(
        Question(R.string.question_africa, false, false, 1),
        Question(R.string.question_australia, true,false, 2),
        Question(R.string.question_oceans, true, false, 3),
        Question(R.string.question_mideast, false, false, 4),
        Question(R.string.question_americas, true, false, 5),
        Question(R.string.question_asia, true, false, 6),
    )

    //    Index variable to operate with
    var currentIndex: Int = 0

    val currentQuestionIndex: Int
        get() = questionBank[currentIndex].questionID

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

//    override fun onCleared() {
//        super.onCleared()
//        Log.d(TAG, "ViewModel instance about to be destroyed")
//    }
}
