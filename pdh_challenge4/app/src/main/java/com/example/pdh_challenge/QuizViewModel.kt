package com.example.pdh_challenge

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, true),
        Question(R.string.question_africa, true),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    var currentIndex = 0
    var isCheater = false
    var isAnswerShown = false
    var correctCount: Float = 0f
    var faultCount: Float = 0f

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    val currentQuestionPassed: Boolean
        get() = questionBank[currentIndex].passed

    val currentQuestionSolved: Boolean
        get() = questionBank[currentIndex].solved

    val currentQuestionCheated: Boolean
        get() = questionBank[currentIndex].cheated

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev() {
        currentIndex = if (currentIndex - 1 < 0){
            questionBank.size - 1
        }else{
            currentIndex - 1
        }
    }

    fun setPassed(passed:Boolean){
        questionBank[currentIndex].passed = passed
    }

    fun setSolved(solved:Boolean){
        questionBank[currentIndex].solved = solved
    }

    fun setCheated(cheated:Boolean){
        questionBank[currentIndex].cheated = cheated
    }

    fun checkEnded():Boolean{
        var result = true
        for(q in questionBank){
            if(!q.solved){
                result =  false
                break
            }
        }
        return result
    }
}