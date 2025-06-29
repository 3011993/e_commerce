package com.example.e_commerce.presentation.product_details

import androidx.lifecycle.SavedStateHandle
import com.example.e_commerce.common.Resources
import com.example.e_commerce.domain.model.ProductModel
import com.example.e_commerce.domain.repo.CommerceRepository
import com.example.e_commerce.domain.service.AccountService
import com.example.e_commerce.domain.service.LogService
import com.example.e_commerce.domain.service.StorageService
import com.example.e_commerce.presentation.CommerceViewModel
import com.example.e_commerce.presentation.PRODUCT_ID
import com.example.e_commerce.presentation.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    logService: LogService,
    private val repo: CommerceRepository,
    storageService: StorageService,
    accountService: AccountService,
    savedStateHandle: SavedStateHandle,
) : CommerceViewModel(logService,storageService,accountService,repo) {

    private val _product = MutableStateFlow<ScreenState<ProductModel>>(ScreenState.Loading())
    val product = _product.asStateFlow()

    init {
        savedStateHandle.get<String>(PRODUCT_ID)?.toInt().let { productId ->
            getProductById(productId!!)
        }
    }

    private fun getProductById(productId: Int) {
        launchCatching(dispatcher = Dispatchers.IO) {
            repo.getProduct(productId).collect { result ->
                when (result) {
                    is Resources.Error -> _product.value =
                        ScreenState.Error(message = result.message)

                    is Resources.Loading -> _product.value = ScreenState.Loading()
                    is Resources.Success -> _product.value =
                        ScreenState.Success(data = result.data!!)
                }
            }
        }
    }

}