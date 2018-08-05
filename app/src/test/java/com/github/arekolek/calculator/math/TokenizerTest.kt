package com.github.arekolek.calculator.math

import org.amshove.kluent.AnyException
import org.amshove.kluent.shouldEqual
import org.amshove.kluent.shouldThrow
import org.junit.Test

class TokenizerTest {

    private val tokenizer = Tokenizer()

    @Test
    fun single() {
        tokenizer.tokenize("1234567890") shouldEqual "1234567890".tokens
    }

    @Test
    fun simple() {
        tokenizer.tokenize("2 + 2") shouldEqual "2 + 2".tokens
    }

    @Test
    fun `no spaces`() {
        tokenizer.tokenize("2+2") shouldEqual "2 + 2".tokens
    }

    @Test
    fun decimals() {
        tokenizer.tokenize("3.14 + 42 - .123 * 123.") shouldEqual "3.14 + 42 - .123 * 123.".tokens

        { tokenizer.tokenize(". + .") } shouldThrow AnyException

        { tokenizer.tokenize("1.2.3") } shouldThrow AnyException
    }

}