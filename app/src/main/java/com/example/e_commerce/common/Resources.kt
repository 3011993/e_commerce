package com.example.e_commerce.common

/**
 * A sealed class representing the different states of a network ordata loading operation.
 * @param T The type of data being loaded.
 * @property data The data that was loaded successfully (in [Success] state) or null in other states.
 * @property message An error message (in [Error] state) or null in other states.*/
sealed class Resources <T>(val data : T? = null, val message : String? = null) {
    class Success<T> (data: T) : Resources<T>(data)
    class Error<T> (message: String, data: T? = null) : Resources<T>(data,message)
    class Loading<T>(data: T? = null) : Resources<T>(data)
}