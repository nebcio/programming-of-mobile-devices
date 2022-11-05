package com.pum.physicsquiz

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CheatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)
        val message = intent.getStringExtra(EXTRA_MESSAGE)
        findViewById<TextView>(R.id.textViewCheated).apply {
            text = message
        }
    }

    fun onClickBackToMain(view: View) {
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}