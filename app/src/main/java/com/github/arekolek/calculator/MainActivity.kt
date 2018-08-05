package com.github.arekolek.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    private val model by lazy {
        ViewModelProviders.of(this).get<MainViewModel>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        model.expression.observe(this) { expression.text = it }
    }

    fun onButtonClick(view: View) {
        val button = view as Button
        model.onButtonClick(button.text)
    }

}