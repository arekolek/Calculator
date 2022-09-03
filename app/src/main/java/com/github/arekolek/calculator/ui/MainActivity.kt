package com.github.arekolek.calculator.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.github.arekolek.calculator.R
import com.github.arekolek.calculator.databinding.MainActivityBinding

class MainActivity : ComponentActivity() {

    private lateinit var binding: MainActivityBinding

    private val viewModel by viewModels<MainViewModel> { ViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.state.observe(this) {
            binding.expression.text = it.expression
            binding.result.text = it.result
        }
    }

    fun onButtonClick(view: View) {
        val button = view as Button
        val key = if (button.id == R.id.backspace) Backspace else Character(button.text.single())
        viewModel.onButtonClick(key)
    }

}
