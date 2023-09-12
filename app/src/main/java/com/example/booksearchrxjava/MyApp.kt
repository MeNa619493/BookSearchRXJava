package com.example.booksearchrxjava

import android.app.Application
import com.example.booksearchrxjava.network.RetroService

class MyApp: Application() {
    val retrofitService by lazy { RetroService.retrofitService }
}