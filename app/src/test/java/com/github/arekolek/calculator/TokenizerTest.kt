package com.github.arekolek.calculator

import org.amshove.kluent.shouldEqual
import org.junit.Test

class TokenizerTest {

    private val tokenizer = Tokenizer()

    @Test
    fun simple() {
        tokenizer.tokenize("2 + 2") shouldEqual "2 + 2".tokens
    }

    @Test
    fun `no spaces`() {
        tokenizer.tokenize("2+2") shouldEqual "2 + 2".tokens
    }

}