package com.example.e_commerce.presentation.product_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.e_commerce.common.Resources
import com.example.e_commerce.domain.model.ProductModel
import com.example.e_commerce.domain.repo.CommerceRepository
import com.example.e_commerce.presentation.PRODUCT_ID
import com.example.e_commerce.presentation.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val repo: CommerceRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _product = MutableStateFlow<ScreenState<ProductModel>>(ScreenState.Loading())
    val product = _product.asStateFlow()

    init {
        savedStateHandle.get<String>(PRODUCT_ID)?.toInt().let { productId ->
            getProductById(productId!!)
        }
    }

    private fun getProductById(productId: Int) {
        viewModelScope.launch {
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