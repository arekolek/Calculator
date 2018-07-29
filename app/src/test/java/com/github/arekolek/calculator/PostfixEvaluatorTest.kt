package com.github.arekolek.calculator

import org.amshove.kluent.shouldEqual
import org.junit.Test
import java.math.MathContext

class PostfixEvaluatorTest {

    private val evaluator = PostfixEvaluator()

    @Test
    fun addition() {
        evaluator.evaluate("2 3 +".tokens) shouldEqualInt 2 + 3
    }

    @Test
    fun subtraction() {
        evaluator.evaluate("2 3 -".tokens) shouldEqualInt 2 - 3
    }

    @Test
    fun multiplication() {
        evaluator.evaluate("2 3 *".tokens) shouldEqualInt 2 * 3
    }

    @Test
    fun division() {
        evaluator.evaluate("2 3 /".tokens) shouldEqual 2.toBigDecimal().divide(3.toBigDecimal(), MathContext.DECIMAL128)
    }

}
