package com.bignerdranch.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView:TextView
    private var answered = false
    private var countOfCorrect = 0
    private val TAG = "MainActivity"
    private val QUESTION_INDEX = "question_index"

    private val quizViewModel: QuizViewModel by
    lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)
        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex
        val lastQuestion = quizViewModel.lastQuestion
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
            if (quizViewModel.currentIndex == lastQuestion){
                nextButton.isEnabled = false
                result(countOfCorrect,lastQuestion+1)
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
            if (quizViewModel.currentIndex == lastQuestion){
                nextButton.isEnabled = false
                result(countOfCorrect,lastQuestion+1)
            }
        }
        nextButton.setOnClickListener {
            if (quizViewModel.currentIndex == lastQuestion){
                nextButton.isEnabled = false
            }
            else{
                answered = false
                trueButton.isEnabled = true
                falseButton.isEnabled = true
                quizViewModel.moveToNext()
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
    override fun onSaveInstanceState(savedInstanceState: Bundle)
    {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)

        trueButton.visibility = View.VISIBLE
        falseButton.visibility = View.VISIBLE
    }
    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer =  quizViewModel.currentQuestionAnswer
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
    private fun result(countOfCorrect: Int, lastQuestion: Int){
        val res = "Count of correct answers: $countOfCorrect/$lastQuestion"
        Toast.makeText(this, res, Toast.LENGTH_SHORT).show()
    }
}