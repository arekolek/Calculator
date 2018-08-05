package com.github.arekolek.calculator.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.github.arekolek.calculator.math.Evaluator
import com.github.arekolek.calculator.math.ExpressionEvaluationException
import io.reactivex.Scheduler
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.min

class MainViewModel(
    private val evaluator: Evaluator,
    scheduler: Scheduler = Schedulers.single()
) : ViewModel() {

    private val processor = PublishProcessor.create<Char>()

    val state: LiveData<UiState> = processor
        .observeOn(scheduler)
        .scan(UiState(), this::computeNextState)
        .toLiveData()

    private fun computeNextState(state: UiState, key: Char): UiState {
        val expression = when (key) {
            '⌫' -> state.expression.dropLast(1)
            else -> state.expression + key
        }
        val result = try {
            evaluator.evaluate(expression.normalize()).format()
        } catch (e: ExpressionEvaluationException) {
            state.result.takeUnless { expression.isEmpty() }.orEmpty()
        }
        return UiState(expression, result)
    }

    fun onButtonClick(key: Char) {
        processor.onNext(key)
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
