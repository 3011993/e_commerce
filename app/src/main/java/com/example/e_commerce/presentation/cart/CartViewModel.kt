package com.example.e_commerce.presentation.cart

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.domain.model.CartItemModel
import com.example.e_commerce.domain.model.CartModel
import com.example.e_commerce.domain.repo.CommerceRepository
import com.example.e_commerce.domain.service.AccountService
import com.example.e_commerce.domain.service.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repo: CommerceRepository,
    private val storageService: StorageService,
    private val auth: AccountService,
) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val userId = auth.currentUserId
            val cartExists = storageService.checkIfCartExists(userId)
            if (!cartExists) {
                val newCart = CartModel(userId = userId)
                createCart(newCart)
            }
        }
    }

    private fun createCart(cart: CartModel) {
        viewModelScope.launch (Dispatchers.IO){
            if (cart.cartId.isBlank()) {
                storageService.saveCart(cart)
            } else {
                storageService.updateCart(cart)
            }
        }
    }

    fun addProductToCart( newItem: CartItemModel) {
        viewModelScope.launch (Dispatchers.IO){
            storageService.addToCart(auth.currentUserId,newItem)
        }
    }
}