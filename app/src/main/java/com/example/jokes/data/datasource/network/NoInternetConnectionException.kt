package com.example.jokes.data.datasource.network

import okio.IOException

class NoInternetConnectionException : IOException() {
    override val message: String
        get() = "You are offline"
}