package com.example.e_commerce.presentation.cart

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.data.service.StorageServiceImpl.Companion.CARTS_COLLECTION
import com.example.e_commerce.domain.model.CartItemModel
import com.example.e_commerce.domain.model.CartModel
import com.example.e_commerce.domain.repo.CommerceRepository
import com.example.e_commerce.domain.service.AccountService
import com.example.e_commerce.domain.service.StorageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repo: CommerceRepository,
    private val storageService: StorageService,
    private val auth: AccountService,
) : ViewModel() {

    private val _carts = MutableStateFlow<List<CartModel>>(emptyList())
    val carts = _carts.asStateFlow()
    init {
      checkBeforeCreateCart()
    }
    private fun checkBeforeCreateCart(){
        viewModelScope.launch(Dispatchers.IO) {
            auth.currentUser.collect { user ->
                if (user != null) {
                    val cartExists = storageService.checkIfCartExists(user.id)
                    if (!cartExists) {
                        val newCart = CartModel(userId = user.id)
                        createCart(newCart)
                    }
                    getCarts() // Call getCarts after user is authenticated
                }
            }
        }
    }
    private fun getCarts(){
        viewModelScope.launch {
            storageService.carts.collect{
                _carts.value = it
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
        Log.i("userACCountCreate",auth.currentUserId)
    }

    fun addProductToCart( newItem: CartItemModel) {
        viewModelScope.launch (Dispatchers.IO){
            storageService.addToCart(auth.currentUserId,newItem)
        }
        Log.i("userACCountadd",auth.currentUserId)

    }
}