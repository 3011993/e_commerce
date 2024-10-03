package com.example.e_commerce.presentation.cart

import android.util.Log
import android.widget.Toast
import com.example.e_commerce.R
import com.example.e_commerce.common.snackbar.SnackBarManager
import com.example.e_commerce.data.repo.CommerceRepositoryImpl
import com.example.e_commerce.domain.model.CartModel
import com.example.e_commerce.domain.model.ProductModel
import com.example.e_commerce.domain.repo.CommerceRepository
import com.example.e_commerce.domain.service.AccountService
import com.example.e_commerce.domain.service.LogService
import com.example.e_commerce.domain.service.StorageService
import com.example.e_commerce.presentation.CommerceViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID
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

    fun addOrUpdateCart(productModel: ProductModel) {
        val cartId =
            repo.getCartIdForProduct(productModel.id.toString()) ?: UUID.randomUUID().toString()
        val cart = CartModel(
            cartId = cartId,
            image = productModel.image,
            title = productModel.title,
            price = productModel.price.toDouble(),
            quantity = 1,
            productId = productModel.id,
            originalPrice = productModel.price.toDouble(),
        )
        launchCatching {
            storageService.addOrUpdateCart(cart) { result ->
                if (result) {
                    SnackBarManager.showMessage(R.string.added_successfully)
                } else {
                    SnackBarManager.showMessage(R.string.out_of_stock)
                }

            }
        }
        if (repo.getCartIdForProduct(productModel.id.toString()) == null) {
            repo.saveCartIdForProduct(productModel.id.toString(), cartId)
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