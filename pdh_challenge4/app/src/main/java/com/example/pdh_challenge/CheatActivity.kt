package com.example.pdh_challenge

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

const val EXTRA_ANSWER_SHOWN = "com.example.pdh_challenge.answer_shown"
private const val EXTRA_ANSWER_IS_TRUE = "com.example.pdh_challenge.answer_is_true"

class CheatActivity : AppCompatActivity() {

    private lateinit var answerTextView:TextView
    private lateinit var showAnswerButton:Button
    private val quizViewModel:QuizViewModel by lazy {
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    private var answerIsTrue = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)
        showAnswerButton.setOnClickListener {
            showAnswer()
            quizViewModel.isAnswerShown = true
            setAnswerShownResult(quizViewModel.isAnswerShown)
        }

        if (quizViewModel.isAnswerShown){
            showAnswer()
            setAnswerShownResult(quizViewModel.isAnswerShown)
        }
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean){
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    private fun showAnswer(){
        val answerText = when{
            answerIsTrue -> R.string.true_button
            else -> R.string.false_button
        }
        answerTextView.setText(answerText)
    }

    companion object{
        fun newIntent(packageContext:Context, answerIsTrue:Boolean):Intent{
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }
}