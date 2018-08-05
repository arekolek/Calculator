package com.github.arekolek.calculator.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.github.arekolek.calculator.R
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

    private val model by lazy {
        ViewModelProviders.of(this, ViewModelFactory()).get<MainViewModel>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        model.state.observe(this) {
            expression.text = it.expression
            result.text = it.result
        }
    }

    fun onButtonClick(view: View) {
        val button = view as Button
        model.onButtonClick(button.text.single())
    }

}