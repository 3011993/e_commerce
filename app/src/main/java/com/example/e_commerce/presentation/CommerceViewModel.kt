package com.example.e_commerce.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.common.snackbar.SnackBarManager
import com.example.e_commerce.common.snackbar.SnackBarMessage.Companion.toSnackBarMessage
import com.example.e_commerce.domain.service.LogService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class CommerceViewModel(private val logService: LogService) : ViewModel() {
    fun launchCatching(snackBar: Boolean = true, dispatcher : CoroutineDispatcher = Dispatchers.Main , block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(dispatcher +
            CoroutineExceptionHandler { _, throwable ->
                if (snackBar) {
                    SnackBarManager.showMessage(throwable.toSnackBarMessage())
                }
                logService.logNonFatalCrash(throwable)
            }, block = block
        )
    }
}