package com.github.arekolek.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _expression = MutableLiveData<String>().apply { value = "" }

    val expression: LiveData<String> get() = _expression

    fun onButtonClick(text: CharSequence) {
        _expression.value += text
    }

}
