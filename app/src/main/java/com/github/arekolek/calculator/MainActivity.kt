package com.github.arekolek.calculator

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        findViewById<ConstraintLayout>(R.id.content)
            .children
            .filterIsInstance<Button>()
            .forEach { button ->
                button.setOnClickListener {
                    expression.text = expression.text.toString() + button.text
                }
            }
    }

}