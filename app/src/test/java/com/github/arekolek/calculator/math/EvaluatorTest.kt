package com.github.arekolek.calculator.math

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.math.BigDecimal

@RunWith(MockitoJUnitRunner::class)
class EvaluatorTest {

    @Test
    fun addition() {
        val tokenizer = mock<Tokenizer> {
            on { tokenize("2+2") } doReturn "2 + 2".tokens
        }
        val converter = mock<NotationConverter> {
            on { toPostfix("2 + 2".tokens) } doReturn "2 2 +".tokens
        }
        val postfixEvaluator = mock<PostfixEvaluator> {
            on { evaluate("2 2 +".tokens) } doReturn BigDecimal(2 + 2)
        }

        Evaluator(tokenizer, converter, postfixEvaluator).evaluate("2+2") shouldEqualInt 2 + 2
    }

    @Test
    fun subtraction() {
        val tokenizer = mock<Tokenizer> {
            on { tokenize("2-2") } doReturn "2 - 2".tokens
        }
        val converter = mock<NotationConverter> {
            on { toPostfix("2 - 2".tokens) } doReturn "2 2 -".tokens
        }
        val postfixEvaluator = mock<PostfixEvaluator> {
            on { evaluate("2 2 -".tokens) } doReturn BigDecimal(2 - 2)
        }

        Evaluator(tokenizer, converter, postfixEvaluator).evaluate("2-2") shouldEqualInt 2 - 2
    }

    @Test
    fun multiplication() {
        val tokenizer = mock<Tokenizer> {
            on { tokenize("2*3") } doReturn "2 * 3".tokens
        }
        val converter = mock<NotationConverter> {
            on { toPostfix("2 * 3".tokens) } doReturn "2 3 *".tokens
        }
        val postfixEvaluator = mock<PostfixEvaluator> {
            on { evaluate("2 3 *".tokens) } doReturn BigDecimal(2 * 3)
        }

        Evaluator(tokenizer, converter, postfixEvaluator).evaluate("2*3") shouldEqualInt 2 * 3
    }

    @Test
    fun division() {
        val tokenizer = mock<Tokenizer> {
            on { tokenize("2/3") } doReturn "2 / 3".tokens
        }
        val converter = mock<NotationConverter> {
            on { toPostfix("2 / 3".tokens) } doReturn "2 3 /".tokens
        }
        val postfixEvaluator = mock<PostfixEvaluator> {
            on { evaluate("2 3 /".tokens) } doReturn 2.toBigDecimal() / 3.toBigDecimal()
        }

        val evaluator = Evaluator(tokenizer, converter, postfixEvaluator)

        evaluator.evaluate("2/3") shouldBeEqualTo 2.toBigDecimal() / 3.toBigDecimal()
    }
}
