package com.github.arekolek.calculator

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.within
import org.junit.Test

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

    private val precision = within(0.00000000000001.toBigDecimal())

    @Test
    fun `integer division`() {
        assertThat(evaluator.evaluate("2 3 /".tokens))
            .isCloseTo(0.6666666666666666666.toBigDecimal(), precision)
    }

    @Test
    fun `decimal division`() {
        assertThat(evaluator.evaluate("8.313744394205266 98.65406305010782 /".tokens))
            .isCloseTo(0.084271687725447206775022352028.toBigDecimal(), precision)
    }

}
