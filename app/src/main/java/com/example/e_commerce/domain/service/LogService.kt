package com.example.e_commerce.domain.service

interface LogService {
fun logNonFatalCrash(throwable: Throwable)
}