package com.github.arekolek.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.arekolek.calculator.math.Evaluator
import com.github.arekolek.calculator.math.ExpressionEvaluationException
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.min

class MainViewModel(private val evaluator: Evaluator) : ViewModel() {

    private val _state = MutableLiveData<UiState>()
    val state: LiveData<UiState> get() = _state

    fun onButtonClick(text: CharSequence) {
        val expression = nextExpression(text)
        val result = evaluate(expression)
        _state.value = UiState(expression, result)
    }

    private fun nextExpression(text: CharSequence): String {
        return _state
            .value
            ?.expression
            .orEmpty()
            .run {
                when (text) {
                    "⌫" -> dropLast(1)
                    else -> plus(text)
                }
            }
    }

    private fun evaluate(expression: String): String {
        return try {
            if (expression.isEmpty()) {
                ""
            } else {
                evaluator.evaluate(expression.normalize()).format()
            }
        } catch (e: ExpressionEvaluationException) {
            _state.value?.result.orEmpty()
        }
    }

    private fun BigDecimal.format(length: Int = 15): String {
        val wholeDigits = precision() - scale()
        val maxScale = if (wholeDigits == 0) length - 2 else length - wholeDigits - 1
        val scale = min(scale(), maxScale)
        return setScale(scale, RoundingMode.DOWN).toPlainString()
    }

    private fun String.normalize(): String {
        return map {
            when (it) {
                '−' -> '-'
                '×' -> '*'
                '÷' -> '/'
                else -> it
            }
        }.joinToString("")
    }

}
