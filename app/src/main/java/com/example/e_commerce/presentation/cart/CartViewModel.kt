package com.example.e_commerce.presentation.cart

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.data.service.StorageServiceImpl.Companion.CARTS_COLLECTION
import com.example.e_commerce.domain.model.CartModel
import com.example.e_commerce.domain.repo.CommerceRepository
import com.example.e_commerce.domain.service.AccountService
import com.example.e_commerce.domain.service.LogService
import com.example.e_commerce.domain.service.StorageService
import com.example.e_commerce.presentation.CommerceViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    logService: LogService,
    private val repo: CommerceRepository,
    private val storageService: StorageService,
    private val auth: AccountService,
) : CommerceViewModel(logService) {

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

    fun createCart(cart: CartModel) {
        launchCatching {
            if (cart.cartId.isBlank()) {
                storageService.saveCart(cart)
            } else {
                addProductToCart(cart)
            }
        }
        Log.i("userACCountCreate", auth.currentUserId)
    }

    fun addProductToCart(cart: CartModel) {
        launchCatching {
            storageService.addToCart(cart)
        }
        Log.i("userACCountadd", auth.currentUserId)

    }

    fun removeProductFromCart(cart: CartModel) {
        launchCatching {
            storageService.removeFromCart(cart)
        }
    }
}