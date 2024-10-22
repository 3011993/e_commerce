package com.example.e_commerce.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.R
import com.example.e_commerce.common.snackbar.SnackBarManager
import com.example.e_commerce.common.snackbar.SnackBarMessage.Companion.toSnackBarMessage
import com.example.e_commerce.domain.model.CartModel
import com.example.e_commerce.domain.model.ProductModel
import com.example.e_commerce.domain.repo.CommerceRepository
import com.example.e_commerce.domain.service.AccountService
import com.example.e_commerce.domain.service.LogService
import com.example.e_commerce.domain.service.StorageService
import com.example.e_commerce.presentation.account.settings.SettingsUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.UUID
import kotlin.math.log

abstract class BaseCommerceViewModel(private val logService: LogService) : ViewModel() {
    fun launchCatching(
        snackBar: Boolean = true,
        dispatcher: CoroutineDispatcher = Dispatchers.Main,
        block: suspend CoroutineScope.() -> Unit,
    ) {
        viewModelScope.launch(
            dispatcher +
                    CoroutineExceptionHandler { _, throwable ->
                        if (snackBar) {
                            SnackBarManager.showMessage(throwable.toSnackBarMessage())
                        }
                        logService.logNonFatalCrash(throwable)
                    }, block = block
        )
    }
}

abstract class BaseCommerceViewModelWithAccountService(
    logService: LogService, accountService: AccountService
) : BaseCommerceViewModel(logService) {
    val uiState = accountService.currentUser.map { SettingsUiState(it.isAnonymous) }
}

abstract class CommerceViewModel(
    logService: LogService,
    private val storageService: StorageService,
    accountService: AccountService,
    private val repo: CommerceRepository,
) : BaseCommerceViewModelWithAccountService(logService, accountService) {
    protected val _allProducts =
        MutableStateFlow<ScreenState<List<ProductModel>>>(ScreenState.Loading())
    val allProducts = _allProducts.asStateFlow()

    protected val _carts = MutableStateFlow<List<CartModel>>(emptyList())
    val carts = _carts.asStateFlow()


    private val _inStock = MutableStateFlow<Map<String, Boolean>>(emptyMap())
    val inStock = _inStock.asStateFlow()

    protected fun getCarts() {
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

    fun getIsInStockStatus(products: List<ProductModel>) {
        products.forEach { product ->
            storageService.getInStockStatus(product.id.toString()) { inStock ->
                this._inStock.value += (product.id.toString() to inStock)
            }
        }
    }

}