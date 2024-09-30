package com.example.e_commerce.presentation.home

import com.example.e_commerce.common.Resources
import com.example.e_commerce.data.Trie
import com.example.e_commerce.domain.model.ProductModel
import com.example.e_commerce.domain.repo.CommerceRepository
import com.example.e_commerce.domain.service.LogService
import com.example.e_commerce.presentation.CommerceViewModel
import com.example.e_commerce.presentation.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    logService: LogService,
    private val repo: CommerceRepository,
) : CommerceViewModel(logService) {
    private val _allProducts =
        MutableStateFlow<ScreenState<List<ProductModel>>>(ScreenState.Loading())
    val allProducts = _allProducts.asStateFlow()

    private var trie: Trie? = null

    init {
        getAllProducts()
    }

    fun getAllProducts() {
        launchCatching(dispatcher = Dispatchers.IO) {
            repo.getProducts().collect { result ->
                when (result) {
                    is Resources.Error -> {
                        _allProducts.value = ScreenState.Error(
                            message = result.message ?: "Unknown error occurred",
                            data = result.data
                        )
                        trie = Trie.preprocessProducts(result.data ?: emptyList())
                    }

                    is Resources.Loading -> {
                        _allProducts.value = ScreenState.Loading()
                    }

                    is Resources.Success -> {
                        _allProducts.value = ScreenState.Success(result.data ?: emptyList())
                        trie = Trie.preprocessProducts(result.data ?: emptyList())
                    }

                }
            }
        }
    }

    fun getProductsByCategory(category: CategoriesEntries) {
        launchCatching(dispatcher = Dispatchers.IO) {
            repo.getProductsByCategory(category.category).collect { result ->
                when (result) {
                    is Resources.Error -> {
                        _allProducts.value = ScreenState.Error(
                            message = result.message ?: "Unknown error occurred",
                            data = result.data ?: emptyList()
                        )
                        trie = Trie.preprocessProducts(result.data ?: emptyList())
                    }

                    is Resources.Loading -> {
                        _allProducts.value = ScreenState.Loading()
                    }

                    is Resources.Success -> {
                        _allProducts.value =
                            ScreenState.Success(result.data ?: emptyList())
                        trie = Trie.preprocessProducts(result.data ?: emptyList())

                    }
                }
            }
        }
    }

    fun onFavouriteClicked(product: ProductModel) {
        launchCatching(dispatcher = Dispatchers.IO) {
            repo.updateFavouriteStatus(product.id, isFavourite = true)
        }
    }

    fun searchProducts(prefix: String): List<ProductModel> {
        return trie?.searchPrefix(prefix) ?: emptyList()
    }

}