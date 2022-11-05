package com.pum.physicsquiz


import android.content.Intent
import android.content.Intent.CATEGORY_BROWSABLE
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible

const val EXTRA_MESSAGE = "com.pum.physicsquiz.explicitintentkotlin.MESSAGE"

class MainActivity : AppCompatActivity() {
    private var currentPosition = 1
    private var points = 0
    private var cheats = 0
    private var rightAns = 0

    private val questions: List<Question> = Questions.questions
    private var question = questions[currentPosition - 1]
    private val urlGoogle = "https://www.google.com/search?q="

    private val questionText: TextView by lazy { findViewById(R.id.textViewQuestion)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("currentPosition")
            points = savedInstanceState.getInt("points")
            cheats = savedInstanceState.getInt("cheats")
            rightAns = savedInstanceState.getInt("rightAns")
        }
        setQuestion()


    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.putInt("points", points)
        outState.putInt("currentPosition", currentPosition)
        outState.putInt("cheats", cheats)
        outState.putInt("rightAns", rightAns)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    private fun setQuestion() {
        question = questions[currentPosition - 1]
        questionText.text = question.question
    }

    private fun showSummary() {
        val trueB: Button by lazy { findViewById(R.id.buttonTrue)}
        trueB.isVisible = false
        val falseB: Button by lazy { findViewById(R.id.buttonFalse)}
        falseB.isVisible = false
        val chearB: Button by lazy { findViewById(R.id.buttonCheat)}
        chearB.isVisible = false
        questionText.text = "Score: $points\nCorrect: $rightAns\nCheated: $cheats"
    }

    fun onClickButtonAns(view: android.view.View) {
        var isCorrect = question.correctAnswer == (view as Button).text.toString().toBoolean()
        if(isCorrect) {
            points += 10
            rightAns++
        }
        if (currentPosition < questions.size) {
            currentPosition++
            setQuestion()
        }
        else showSummary()
    }

    fun onClickButtonCheat(view: android.view.View) {
        val intent = Intent(this, CheatActivity::class.java).apply {
            putExtra(EXTRA_MESSAGE, question.correctAnswer.toString())
        }
        startActivity(intent)
        points -= 15
        cheats++
        if (currentPosition < questions.size) {
            currentPosition++
            setQuestion()
        }
        else showSummary()
    }

    fun onClickQuestion(view: android.view.View) {
        val urlSearch = urlGoogle + question.question.replace(" ", "+")
        val intentSearch = Intent(Intent.ACTION_VIEW, Uri.parse(urlSearch)).apply{
            addCategory(CATEGORY_BROWSABLE)
        }
        if (intent.resolveActivity(packageManager) != null)
            startActivity(intentSearch)

    }
}


