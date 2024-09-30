package com.example.e_commerce.presentation.wishlist

import com.example.e_commerce.common.Resources
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
class WishListViewModel @Inject constructor(
    logService: LogService,
    private val repo: CommerceRepository,
) : CommerceViewModel(logService) {
    private val _allProducts =
        MutableStateFlow<ScreenState<List<ProductModel>>>(ScreenState.Loading())
    val allProducts = _allProducts.asStateFlow()

    init {
        getFavouriteProducts()
    }

    private fun getFavouriteProducts() {
        launchCatching(dispatcher = Dispatchers.IO) {
            repo.getFavouriteProducts().collect { result ->
                when (result) {
                    is Resources.Error -> _allProducts.value =
                        ScreenState.Error(result.message, data = result.data)

                    is Resources.Loading -> _allProducts.value = ScreenState.Loading()
                    is Resources.Success -> _allProducts.value =
                        ScreenState.Success(result.data ?: emptyList())
                }
            }
        }
    }

}
