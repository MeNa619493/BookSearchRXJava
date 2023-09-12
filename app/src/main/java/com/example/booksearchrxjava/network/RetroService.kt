package com.example.booksearchrxjava.network

import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroService {

    @GET("volumes")
    fun getBookListFromApi(@Query("q") query: String): Single<BookListModel>

    companion object {
        val retrofitService: RetroService by lazy {
            RetroInstance.getRetroInstance().create(RetroService::class.java)
        }
    }
}