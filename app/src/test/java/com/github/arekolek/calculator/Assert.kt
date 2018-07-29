package com.github.arekolek.calculator

import org.amshove.kluent.shouldEqual
import java.math.BigDecimal

infix fun BigDecimal.shouldEqualInt(expected: Int): BigDecimal =
    this shouldEqual expected.toBigDecimal()
