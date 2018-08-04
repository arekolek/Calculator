package com.github.arekolek.calculator

import java.math.BigDecimal

class Evaluator(
    private val tokenizer: Tokenizer,
    private val converter: NotationConverter,
    private val postfixEvaluator: PostfixEvaluator
) {

    fun evaluate(expression: String): BigDecimal {
        try {
            val tokens = tokenizer.tokenize(expression)
            return postfixEvaluator.evaluate(converter.toPostfix(tokens))
        } catch (e: Exception) {
            throw ExpressionEvaluationException("Could not evaluate: $expression", e)
        }
    }

}
