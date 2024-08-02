package com.malenikajkat.classmate.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Добро пожаловать в приложение Classmate"
    }
    val text: LiveData<String> = _text
}