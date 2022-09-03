package com.github.arekolek.calculator.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.github.arekolek.calculator.R
import com.github.arekolek.calculator.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    private val model by lazy {
        ViewModelProviders.of(this, ViewModelFactory()).get<MainViewModel>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        model.state.observe(this) {
            binding.expression.text = it.expression
            binding.result.text = it.result
        }
    }

    fun onButtonClick(view: View) {
        val button = view as Button
        val key = if (button.id == R.id.backspace) Backspace else Character(button.text.single())
        model.onButtonClick(key)
    }

}
