package com.example.e_commerce.presentation.check_out

import com.example.e_commerce.domain.repo.CommerceRepository
import com.example.e_commerce.domain.service.AccountService
import com.example.e_commerce.domain.service.LogService
import com.example.e_commerce.domain.service.StorageService
import com.example.e_commerce.presentation.CommerceViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderConfirmationViewModel @Inject constructor(
    logService: LogService,
    accountService: AccountService,
    storageService: StorageService,
    repo: CommerceRepository,
) :
    CommerceViewModel(logService, storageService, accountService, repo) {
    init {
        getCarts()
    }
}