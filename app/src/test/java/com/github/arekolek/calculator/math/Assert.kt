package com.github.arekolek.calculator.math

import org.amshove.kluent.shouldBeEqualTo
import java.math.BigDecimal

infix fun BigDecimal.shouldEqualInt(expected: Int): BigDecimal =
    this shouldBeEqualTo expected.toBigDecimal()
