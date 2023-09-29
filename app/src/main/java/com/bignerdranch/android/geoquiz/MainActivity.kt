package com.bignerdranch.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView:TextView
    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans,
            true),
        Question(R.string.question_mideast,
            false),
        Question(R.string.question_africa,
            false),
        Question(R.string.question_americas
            , true),
        Question(R.string.question_asia,
            true))
    private var currentIndex = 0
    private var answered = false
    private var countOfCorrect = 0
    private val TAG = "MainActivity"
    private val QUESTION_INDEX = "question_index"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)
        trueButton = findViewById(R.id.true_button)
        falseButton =findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)
        trueButton.setOnClickListener { view: View ->
            if (!answered) {
                checkAnswer(true)
                answered = true
                // Заблокировать кнопки ответов после ответа
                trueButton.isEnabled = false
                falseButton.isEnabled = false
            }
            if (currentIndex == questionBank.size - 1){
                nextButton.isEnabled = false
                result(countOfCorrect)
            }

        }
        falseButton.setOnClickListener { view: View ->
            if (!answered) {
                checkAnswer(false)
                answered = true
                // Заблокировать кнопки ответов после ответа
                trueButton.isEnabled = false
                falseButton.isEnabled = false
            }
            if (currentIndex == questionBank.size - 1){
                nextButton.isEnabled = false
                result(countOfCorrect)
            }
        }
        nextButton.setOnClickListener {
            if (currentIndex == questionBank.size - 1){
                nextButton.isEnabled = false
            }
            else{
                answered = false
                trueButton.isEnabled = true
                falseButton.isEnabled = true
                currentIndex = (currentIndex + 1) % questionBank.size
            }

            updateQuestion()
        }

        updateQuestion()
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
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(QUESTION_INDEX, currentIndex)
        super.onSaveInstanceState(outState)
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentIndex = savedInstanceState.getInt(QUESTION_INDEX)
        updateQuestion()
    }
    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)

        trueButton.visibility = View.VISIBLE
        falseButton.visibility = View.VISIBLE
    }
    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId = if (userAnswer ==
            correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        if (userAnswer == correctAnswer) {
            countOfCorrect+=1
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }
    private fun result(countOfCorrect: Int){
        val res = "Count of correct answers: $countOfCorrect"
        Toast.makeText(this, res, Toast.LENGTH_SHORT).show()
    }
}