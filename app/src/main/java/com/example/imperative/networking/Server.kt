package com.example.imperative.networking

object Server {
    init{
        System.loadLibrary("keys")
    }

    val IS_TESTER = true

    external fun Pruduction(): String
    external fun Development(): String


}