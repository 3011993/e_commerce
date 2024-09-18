package com.example.e_commerce.presentation.cart

import android.util.Log
import com.example.e_commerce.data.repo.CommerceRepositoryImpl
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

    fun addOrUpdateCart(cart: CartModel) {
        launchCatching(dispatcher = Dispatchers.IO){
             storageService.addOrUpdateCart(cart)
        }
        Log.i("userACCountCreate", auth.currentUserId)
    }

    fun removeProductFromCart(cart: CartModel) {
        launchCatching {
            storageService.removeFromCart(cart)
        }
    }
    fun saveCartIdForProduct(productId : String,cartId : String){
        repo.saveCartIdForProduct(productId,cartId)
    }
    fun getCartIdForProduct(productId: String) : String?{
        return repo.getCartIdForProduct(productId)

    }
}