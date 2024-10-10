package com.example.e_commerce.presentation.cart


import com.example.e_commerce.R
import com.example.e_commerce.common.snackbar.SnackBarManager
import com.example.e_commerce.domain.model.CartModel
import com.example.e_commerce.domain.repo.CommerceRepository
import com.example.e_commerce.domain.service.AccountService
import com.example.e_commerce.domain.service.LogService
import com.example.e_commerce.domain.service.StorageService
import com.example.e_commerce.presentation.CommerceViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    logService: LogService,
    repo: CommerceRepository,
    private val storageService: StorageService,
    accountService: AccountService,
) : CommerceViewModel(logService,storageService,accountService,repo) {

    private val _carts = MutableStateFlow<List<CartModel>>(emptyList())
    val carts = _carts.asStateFlow()

    init {
        getCarts()
    }

    private fun getCarts() {
        launchCatching {
            storageService.carts.collect {
                _carts.value = it
            }
        }
    }
    fun updateCart(cart: CartModel) {
        launchCatching {
            storageService.updateCart(cart) { result ->
                if (result) {
                    SnackBarManager.showMessage(R.string.added_successfully)
                } else {
                    SnackBarManager.showMessage(R.string.out_of_stock)
                }

            }
        }
    }

    fun removeProductFromCart(cart: CartModel) {
        launchCatching {
            storageService.removeFromCart(cart) { result ->
                if (result) {
                    SnackBarManager.showMessage(R.string.removed_successfully)
                } else {
                    SnackBarManager.showMessage(R.string.removed_failed)
                }
            }
        }
    }

}