package com.github.arekolek.calculator

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

    @Test
    fun `complex expression`() {
        evaluator.evaluate("((15 / (7 - (1 + 1))) * 3) - (2 + (1 + 1))")
            .shouldEqualInt(((15 / (7 - (1 + 1))) * 3) - (2 + (1 + 1)))
    }

}
