package com.example.pdh_challenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button // lateinit: 추후에 할 초기화를 컴파일러에게 약속함
    private lateinit var falseButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button) // inflate
        falseButton = findViewById(R.id.false_button)

        trueButton.setOnClickListener {
            val t_toast = Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT)
            t_toast.setGravity(Gravity.TOP, 0, 0)
            t_toast.show()
            // API 30 부터 deprecated, snackbar등으로 대체
        }

        falseButton.setOnClickListener {
            Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show()
        }
    }
}