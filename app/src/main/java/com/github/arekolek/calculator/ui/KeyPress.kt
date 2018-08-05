package com.github.arekolek.calculator.ui

sealed class KeyPress

object Backspace : KeyPress()

data class Character(val value: Char) : KeyPress()
