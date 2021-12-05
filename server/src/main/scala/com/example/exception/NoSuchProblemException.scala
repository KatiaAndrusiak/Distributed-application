package com.example.exception

final case class NoSuchProblemException(private val message: String = "",
                                        private val cause: Throwable = None.orNull)
    extends Exception(message, cause)