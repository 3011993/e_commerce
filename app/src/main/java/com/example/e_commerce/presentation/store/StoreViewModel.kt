package com.example.e_commerce.presentation.store

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.common.Resources
import com.example.e_commerce.domain.model.CartModel
import com.example.e_commerce.domain.model.ProductModel
import com.example.e_commerce.domain.repo.CommerceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(private val repo: CommerceRepository) : ViewModel() {
    private val _product = MutableStateFlow<CartModel?>(null)
    val product = _product.asStateFlow()

    init {
        getAllProducts(5)
    }

    private fun getAllProducts(id : Int){
        viewModelScope.launch {
            repo.getCart(id).collect { result ->
                when(result) {
                    is Resources.Error -> {}
                    is Resources.Loading -> {}
                    is Resources.Success -> _product.value = result.data
                }

            }
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            val categories = repo.getCategories()
        }
    }
}