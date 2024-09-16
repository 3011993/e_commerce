package com.example.e_commerce.presentation.categories

import com.example.e_commerce.common.Resources
import com.example.e_commerce.domain.model.ProductModel
import com.example.e_commerce.domain.repo.CommerceRepository
import com.example.e_commerce.domain.service.AccountService
import com.example.e_commerce.domain.service.LogService
import com.example.e_commerce.presentation.CommerceViewModel
import com.example.e_commerce.presentation.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    logService: LogService,
    private val accountService: AccountService,
    private val repo: CommerceRepository,
) : CommerceViewModel(logService) {
    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories = _categories.asStateFlow()

    private val _allProducts =
        MutableStateFlow<ScreenState<List<ProductModel>>>(ScreenState.Loading())
    val allProducts = _allProducts.asStateFlow()

    init {
        getCategories()
        getProductsByCategory("electronics")
        launchCatching {
            accountService.createAnonymousAccount()
        }
    }

    private fun getCategories() {
        launchCatching {
            repo.getCategories().collect {
                _categories.value = it
            }
        }
    }

    fun getProductsByCategory(value: String) {
        launchCatching(dispatcher = Dispatchers.IO){
            repo.getProductsByCategory(value).collect { result ->
                when (result) {
                    is Resources.Error -> {
                        _allProducts.value = ScreenState.Error(
                            message = result.message ?: "Unknown error occurred",
                            data = result.data ?: emptyList()
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
