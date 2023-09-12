package com.example.booksearchrxjava.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.booksearchrxjava.network.RetroService

class MainActivityViewModelFactory(private val retroInstance: RetroService) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainActivityViewModel(retroInstance) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}