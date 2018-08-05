package com.github.arekolek.calculator.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.github.arekolek.calculator.math.Evaluator
import com.github.arekolek.calculator.math.ExpressionEvaluationException
import com.nhaarman.mockitokotlin2.*
import io.reactivex.schedulers.TestScheduler
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.StrictStubs::class)
class MainViewModelTest {

    @get:Rule
    val instantExecutor = InstantTaskExecutorRule()

    private val evaluator: Evaluator = mock {
        on { evaluate("2") } doReturn 2.toBigDecimal()
    }

    private val scheduler = TestScheduler()

    private val model = MainViewModel(evaluator, scheduler)

    private val observer: Observer<UiState> = mock()

    @Test
    fun addition() {
        stubbing(evaluator) {
            on { evaluate("2+") } doThrow ExpressionEvaluationException()
            on { evaluate("2+2") } doReturn 4.toBigDecimal()
        }

        model.state.observeForever(observer)
        model.onButtonClick(Character('2'))
        model.onButtonClick(Character('+'))
        model.onButtonClick(Character('2'))

        scheduler.triggerActions()

        inOrder(observer) {
            verify(observer).onChanged(UiState("2", "2"))
            verify(observer).onChanged(UiState("2+", "2"))
            verify(observer).onChanged(UiState("2+2", "4"))
        }
    }

    @Test
    fun `result should clear after expression is cleared`() {
        stubbing(evaluator) {
            on { evaluate("") } doThrow ExpressionEvaluationException()
        }

        model.state.observeForever(observer)
        model.onButtonClick(Character('2'))
        model.onButtonClick(Backspace)

        scheduler.triggerActions()

        inOrder(observer) {
            verify(observer).onChanged(UiState("2", "2"))
            verify(observer).onChanged(UiState("", ""))
        }
    }

    @Test
    fun `limit precision of decimals less than one`() {
        given(evaluator.evaluate("1"))
            .willReturn(0.592452830188679245283018867924528301886792452830188679245.toBigDecimal())

        model.state.observeForever(observer)
        model.onButtonClick(Character('1'))

        scheduler.triggerActions()

        verify(observer).onChanged(UiState("1", "0.5924528301886"))
    }

    @Test
    fun `limit precision of numbers larger than one`() {
        given(evaluator.evaluate(any()))
            .willReturn(666666.6666666666666666666666666666666666666666666666666666.toBigDecimal())

        model.state.observeForever(observer)
        model.onButtonClick(Character('1'))

        scheduler.triggerActions()

        verify(observer).onChanged(UiState("1", "666666.66666666"))
    }

    @Test
    fun `precision limit should apply to decimal part only`() {
        given(evaluator.evaluate(any()))
            .willReturn("100000000000000000000000".toBigDecimal())

        model.state.observeForever(observer)
        model.onButtonClick(Character('1'))

        scheduler.triggerActions()

        verify(observer).onChanged(UiState("1", "100000000000000000000000"))
    }

}