package com.github.arekolek.calculator.math

import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldThrow
import org.assertj.core.api.AbstractBigDecimalAssert
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.within
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class EvaluatorIntegrationTest {

    private val evaluator = Evaluator(Tokenizer(), NotationConverter(), PostfixEvaluator())

    @Test
    fun `2 + 2`() {
        evaluator.evaluate("2 + 2") shouldEqualInt 2 + 2
    }

    @Test
    fun `1 + 2`() {
        evaluator.evaluate("1 + 2") shouldEqualInt 1 + 2
    }

    @Test
    fun `1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9`() {
        evaluator.evaluate("1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9") shouldEqualInt 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9
    }

    @Test
    fun `2 - 2`() {
        evaluator.evaluate("2 - 2") shouldEqualInt 2 - 2
    }

    @Ignore("Not part of the requirements")
    @Test
    fun negation() {
        evaluator.evaluate("-1") shouldEqualInt -1
    }

    @Test
    fun `complex expression`() {
        evaluator.evaluate("((15 / (7 - (1 + 1))) * 3) - (2 + (1 + 1))")
            .shouldEqualInt(((15 / (7 - (1 + 1))) * 3) - (2 + (1 + 1)))
    }

    @Test
    fun `invalid expression`() {
        val invocation = { evaluator.evaluate("((15 / (7 - (1 + 1) * 3) - (2 + (1 + 1))") }
        invocation shouldThrow ExpressionEvaluationException::class
    }

    @Test
    fun `decimal addition`() {
        evaluator.evaluate("39.14009024234729+58.875814167211324")
            .shouldEqual("39.14009024234729".toBigDecimal() + "58.875814167211324".toBigDecimal())
    }

    @Test
    fun `decimal subtraction`() {
        evaluator.evaluate("39.14009024234729-58.875814167211324")
            .shouldEqual("39.14009024234729".toBigDecimal() - "58.875814167211324".toBigDecimal())
    }

    @Test
    fun `decimal multiplication`() {
        evaluator.evaluate("39.14009024234729*58.875814167211324")
            .shouldEqual("39.14009024234729".toBigDecimal() * "58.875814167211324".toBigDecimal())
    }

    private fun AbstractBigDecimalAssert<*>.isCloseTo(value: String) {
        isCloseTo(
            value.toBigDecimal(),
            within(0.000000000000000000000000000000000000000000000000000001.toBigDecimal())
        )
    }

    @Test
    fun `decimal division`() {
        assertThat(evaluator.evaluate("39.14009024234729/58.875814167211324"))
            .isCloseTo("0.664790641046368662144152276362032447006009143332869676964")
    }

    @Test
    fun `decimal divisor shorter`() {
        assertThat(evaluator.evaluate("39.1400902/58.875814167211324"))
            .isCloseTo("0.664790640327104043913467416883186902385797412713683344138")
    }

    @Test
    fun `decimal divider shorter`() {
        assertThat(evaluator.evaluate("39.14009024234729/58.87581416"))
            .isCloseTo("0.664790641127794639400702938831343033099892507711523084269")
    }

    @Test
    fun `complex decimal expression`() {
        assertThat(evaluator.evaluate("((3.9561 / (93. - (7.92105263285365 + .59535255))) * 26.818) - (4.3 + (59.348093 + 77))"))
            .isCloseTo("-139.392290734132549307446803425820364145770049539004414039")
    }
}
