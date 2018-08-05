package com.github.arekolek.calculator.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.arekolek.calculator.math.Evaluator
import com.github.arekolek.calculator.math.NotationConverter
import com.github.arekolek.calculator.math.PostfixEvaluator
import com.github.arekolek.calculator.math.Tokenizer

class ViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return MainViewModel(Evaluator(Tokenizer(), NotationConverter(), PostfixEvaluator())) as T
    }
}
