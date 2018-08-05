package com.github.arekolek.calculator.math

import org.amshove.kluent.shouldEqual
import org.junit.Test

class NotationConverterTest {

    private val converter = NotationConverter()

    @Test
    fun `addition 2 + 2`() {
        converter.toPostfix("2 + 2".tokens) shouldEqual "2 2 +".tokens
    }

    @Test
    fun `subtraction 3 - 4 + 5`() {
        converter.toPostfix("3 - 4 + 5".tokens) shouldEqual "3 4 - 5 +".tokens
    }

    @Test
    fun `multiplication 3 - 4 * 5`() {
        converter.toPostfix("3 - 4 * 5".tokens) shouldEqual "3 4 5 * -".tokens
    }

    @Test
    fun `parentheses (3 - 4) * 5`() {
        converter.toPostfix("( 3 - 4 ) * 5".tokens) shouldEqual "3 4 - 5 *".tokens
    }

    @Test
    fun `complex expression`() {
        converter.toPostfix("( ( 15 / ( 7 - ( 1 + 1 ) ) ) * 3 ) - ( 2 + ( 1 + 1 ) )".tokens)
            .shouldEqual("15 7 1 1 + - / 3 * 2 1 1 + + -".tokens)
    }

}
