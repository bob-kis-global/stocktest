package com.example.stocktest.data

import android.view.View
import androidx.lifecycle.MutableLiveData

data class Result<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Result<T> {
            return Result(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Result<T> {
            return Result(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Result<T> {
            return Result(Status.LOADING, data, null)
        }
    }

}


enum class Status {
    SUCCESS,
    ERROR,
    LOADING;

    fun showWhenLoading(progressBar: View) {
        progressBar.visibility = if (this == LOADING) View.VISIBLE else View.GONE
    }

    fun hideWhenLoading(view: View) {
        view.visibility = if (this == LOADING) View.GONE else View.VISIBLE
    }
}

fun <T> mutableLoadingLiveDataOf(): MutableLiveData<Result<T>> {
    val data = MutableLiveData<Result<T>>()
    data.postValue(Result.loading(null))
    return data
}