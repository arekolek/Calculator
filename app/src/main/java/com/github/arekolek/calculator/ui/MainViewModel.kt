package com.github.arekolek.calculator.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import com.github.arekolek.calculator.math.Evaluator
import com.github.arekolek.calculator.math.ExpressionEvaluationException
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.min

class MainViewModel(
    private val evaluator: Evaluator,
    scheduler: Scheduler = Schedulers.single()
) : ViewModel() {

    private val processor = PublishProcessor.create<KeyPress>()

    private var disposables = CompositeDisposable()

    val state: LiveData<UiState> = processor
        .observeOn(scheduler)
        .scan(UiState(), this::computeNextState)
        .publish()
        .apply {
            connect { disposables += it }
        }
        .toLiveData()

    private fun computeNextState(state: UiState, key: KeyPress): UiState {
        val expression = when (key) {
            is Backspace -> state.expression.dropLast(1)
            is Character -> state.expression + key.value
        }
        val result = try {
            evaluator.evaluate(expression.normalize()).format()
        } catch (e: ExpressionEvaluationException) {
            state.result.takeUnless { expression.isEmpty() }.orEmpty()
        }
        return UiState(expression, result)
    }

    fun onButtonClick(key: KeyPress) {
        processor.onNext(key)
    }

    private fun BigDecimal.format(length: Int = 15): String {
        val wholeDigits = precision() - scale()
        val maxScale = if (wholeDigits == 0) length - 2 else length - wholeDigits - 1
        val scale = min(scale(), maxScale)
        return setScale(scale, RoundingMode.DOWN).toPlainString()
    }

    private fun String.normalize(): String {
        return replace('−', '-')
            .replace('×', '*')
            .replace('÷', '/')
    }

    override fun onCleared() {
        disposables.clear()
    }

}
