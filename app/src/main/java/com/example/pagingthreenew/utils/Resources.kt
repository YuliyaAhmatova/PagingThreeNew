package com.example.pagingthreenew.utils

sealed class Resources<T> {
    class Success<T>(val data: T) : Resources<T>()
    class Error<T>(val message: String) : Resources<T>()
    class Loading<T> : Resources<T>()
}