package com.example.exception

final case class NoSuchStreetException(private val message: String = "",
                                 private val cause: Throwable = None.orNull)
    extends Exception(message, cause)

