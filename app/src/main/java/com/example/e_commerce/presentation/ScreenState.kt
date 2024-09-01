package com.example.e_commerce.presentation

/**
 * A sealed class representing the different states of a UI operation, such as data loading or an action result.
 *
 * @param T The type of data associated with the UI state.
 */
sealed class ScreenState<T> {
    class Success<T>(val data: T) : ScreenState<T>()
    data class Error<T>(val message: String? = "", val data : T? = null ) : ScreenState<T>()
    class Loading<T> : ScreenState<T>()
}