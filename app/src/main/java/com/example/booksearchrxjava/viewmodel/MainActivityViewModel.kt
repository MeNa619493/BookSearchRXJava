package com.example.booksearchrxjava.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.booksearchrxjava.network.BookListModel
import com.example.booksearchrxjava.network.RetroService
import com.example.booksearchrxjava.util.NetworkResult
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class MainActivityViewModel(private val retroInstance: RetroService): ViewModel() {
    private val _bookList = MutableLiveData<NetworkResult<BookListModel>>()
    val bookList: LiveData<NetworkResult<BookListModel>>
        get() = _bookList

    private val disposable = CompositeDisposable()

    fun makeApiCall(query: String) {
        _bookList.value = NetworkResult.Loading()
        val sub = retroInstance.getBookListFromApi(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object: DisposableSingleObserver<BookListModel>() {
                override fun onSuccess(bookListModel: BookListModel) {
                    _bookList.value = NetworkResult.Success(bookListModel)
                }

                override fun onError(error: Throwable) {
                    _bookList.value = NetworkResult.Error(error.toString())
                }
            })
        disposable.add(sub)
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}