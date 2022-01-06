package com.example.pdh_challenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button // lateinit: 추후에 할 초기화를 컴파일러에게 약속함
    private lateinit var falseButton: Button
    private lateinit var nextButton:ImageButton
    private lateinit var prevButton:ImageButton
    private lateinit var questionTextView: TextView
    private var correctCount: Float = 0f
    private var faultCount: Float = 0f

    private val questionBank = listOf(
        Question(R.string.question_australia, true, false, false),
        Question(R.string.question_oceans, true, false, false),
        Question(R.string.question_mideast, true, false, false),
        Question(R.string.question_africa, true, false, false),
        Question(R.string.question_americas, true,false, false),
        Question(R.string.question_asia, true, false, false)
        )

    private var currentIndex = 0

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestory called")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button) // inflate
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener {
            checkAnswer(true)
            questionBank[currentIndex].solved = true
            checkPassed(currentIndex)
            checkEnded()
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
            questionBank[currentIndex].solved = true
            checkPassed(currentIndex)
            checkEnded()
        }

        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
            checkPassed(currentIndex)
        }

        prevButton.setOnClickListener {
            currentIndex = if (currentIndex - 1 < 0){
                questionBank.size - 1
            }else{
                currentIndex - 1
            }
            updateQuestion()
            checkPassed(currentIndex)
        }

        questionTextView.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        updateQuestion()
    }

    private fun updateQuestion(){
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = questionBank[currentIndex].answer
        val result = userAnswer == correctAnswer
        val messageResId = if (result){
            R.string.correct_toast
        }else{
            R.string.incorrect_toast
        }

        if (result){
            correctCount += 1
            questionBank[currentIndex].passed = true
        }
        else{
            faultCount += 1
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    private fun checkPassed(currentIndex:Int){
        if(questionBank[currentIndex].passed){
            trueButton.isEnabled = false
            falseButton.isEnabled = false
        }
        else{
            trueButton.isEnabled = true
            falseButton.isEnabled = true
        }
    }

    private fun checkEnded(){
        var result = true
        for(q in questionBank){
            if(!q.solved){
               result =  false
                break
            }
        }

        if(result){
            val resultRate = (correctCount/(correctCount + faultCount)) * 100
            Toast.makeText(this, "모든 문제를 풀었습니다. 정답률 ${resultRate.toInt()}%", Toast.LENGTH_SHORT).show()
        }
    }
}