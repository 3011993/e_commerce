package com.example.e_commerce.presentation.wishlist

import com.example.e_commerce.common.Resources
import com.example.e_commerce.domain.model.ProductModel
import com.example.e_commerce.domain.repo.CommerceRepository
import com.example.e_commerce.domain.service.AccountService
import com.example.e_commerce.domain.service.LogService
import com.example.e_commerce.domain.service.StorageService
import com.example.e_commerce.presentation.CommerceViewModel
import com.example.e_commerce.presentation.Home
import com.example.e_commerce.presentation.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class WishListViewModel @Inject constructor(
    logService: LogService,
    private val repo: CommerceRepository,
    accountService: AccountService,
    storageService: StorageService,
) : CommerceViewModel(logService, storageService, accountService, repo) {

    init {
        getFavouriteProducts()
    }

    private fun getFavouriteProducts() {
        launchCatching(dispatcher = Dispatchers.IO) {
            repo.getFavouriteProducts().collect { result ->
                when (result) {
                    is Resources.Error -> {
                        _allProducts.value =
                            ScreenState.Error(result.message, data = result.data)
                        getIsInStockStatus(result.data ?: emptyList())
                    }

                    is Resources.Loading -> _allProducts.value = ScreenState.Loading()
                    is Resources.Success -> {
                        _allProducts.value =
                            ScreenState.Success(result.data ?: emptyList())
                        getIsInStockStatus(result.data ?: emptyList())
                    }
                }
            }
        }
    }

    fun onFavouriteClicked(product: ProductModel) {
        launchCatching(dispatcher = Dispatchers.IO) {
            if (product.isFavorite) {
                val products = repo.removeFavouriteFromWishList(product.id, isFavourite = false)
                _allProducts.value = ScreenState.Success(data = products)
            } else {
                val products = repo.addFavouriteFromWishList(product.id, isFavourite = true)
                _allProducts.value = ScreenState.Success(data = products)
            }
        }
    }

    fun onNavigateBackClicked(clearAndNavigate: (String) -> Unit) {
        clearAndNavigate(Home.route)
    }

}
