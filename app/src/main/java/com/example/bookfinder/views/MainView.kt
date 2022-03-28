package com.example.bookfinder.views

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bookfinder.models.BookListModel
import com.example.bookfinder.networks.RetroInstance
import com.example.bookfinder.networks.RetroService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainView: ViewModel() {
    private var bookList: MutableLiveData<BookListModel>? = null

    init {
        this.bookList = MutableLiveData()
    }

    fun getObserver(): MutableLiveData<BookListModel> {
        return this.bookList!!
    }

    fun call(query: String) {
        val retroInstance = RetroInstance.getRetroInstance().create(RetroService::class.java)

        retroInstance.getBookListFromApi(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this.getBookListObserverRx())
    }

    private fun getBookListObserverRx(): Observer<BookListModel> {
        return object : Observer<BookListModel> {
            override fun onComplete() {}

            override fun onError(e: Throwable) {
                bookList?.postValue(null)
            }

            override fun onNext(t: BookListModel) {
                bookList?.postValue(t)
            }

            override fun onSubscribe(d: Disposable) {}
        }
    }
}