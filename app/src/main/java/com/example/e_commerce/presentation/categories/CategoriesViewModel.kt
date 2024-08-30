package com.example.e_commerce.presentation.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerce.common.Resources
import com.example.e_commerce.domain.model.ProductModel
import com.example.e_commerce.domain.repo.CommerceRepository
import com.example.e_commerce.presentation.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(private val repo: CommerceRepository) : ViewModel() {
    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories = _categories.asStateFlow()

    private val _allProducts =
        MutableStateFlow<ScreenState<List<ProductModel>>>(ScreenState.Loading())
    val allProducts = _allProducts.asStateFlow()

    init {
        getCategories()
        getProductsByCategory("electronics")
    }

    private fun getCategories() {
        viewModelScope.launch {
            _categories.value = repo.getCategories()
        }
    }

    fun getProductsByCategory(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getProductsByCategory(value).collect { result ->
                when (result) {
                    is Resources.Error -> {
                        _allProducts.value = ScreenState.Error(
                            message = result.message ?: "Unknown error occurred"
                        )
                    }

                    is Resources.Loading -> {
                        _allProducts.value = ScreenState.Loading()
                    }

                    is Resources.Success -> {
                        _allProducts.value =
                            ScreenState.Success(result.data ?: emptyList())
                    }
                }
            }
        }
    }
}
