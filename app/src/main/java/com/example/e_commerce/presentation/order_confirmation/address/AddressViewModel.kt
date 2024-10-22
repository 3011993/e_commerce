package com.example.e_commerce.presentation.order_confirmation.address

import androidx.compose.runtime.mutableStateOf
import com.example.e_commerce.common.snackbar.SnackBarManager
import com.example.e_commerce.domain.model.AddressModel
import com.example.e_commerce.domain.repo.CommerceRepository
import com.example.e_commerce.domain.service.AccountService
import com.example.e_commerce.domain.service.LogService
import com.example.e_commerce.domain.service.StorageService
import com.example.e_commerce.presentation.BaseCommerceViewModel
import com.example.e_commerce.presentation.CommerceViewModel
import com.example.e_commerce.presentation.ORDER_CONFIRMATION
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    logService: LogService, private val repo: CommerceRepository
) : BaseCommerceViewModel(logService) {

    var addressModel = mutableStateOf(AddressModel())
        private set

    private val name : String
        get() = addressModel.value.name
    private val country : String
        get() = addressModel.value.country
    private val phoneNumber : String
        get() = addressModel.value.phoneNumber
    private val address: String
        get() = addressModel.value.address
    private val city: String
        get() = addressModel.value.city


    init {
        getAddress()

    }

    fun onNameChange(newValue: String) {
        addressModel.value = addressModel.value.copy(name = newValue)
    }

    fun onCountryChange(newValue: String) {
        addressModel.value = addressModel.value.copy(country = newValue)
    }

    fun onCityChange(newValue: String) {
        addressModel.value = addressModel.value.copy(city = newValue)
    }

    fun onPhoneNumberChange(newValue: String) {
        addressModel.value = addressModel.value.copy(phoneNumber = newValue)
    }

    fun onAddressChange(newValue: String) {
        addressModel.value = addressModel.value.copy(address = newValue)
    }

    fun onSaveAddressClicked(openScreen: (String) -> Unit) {
        if (name.isBlank()){
            SnackBarManager.showMessage("please add your name")
            return
        }
        if (country.isBlank()){
            SnackBarManager.showMessage("please add your country")
            return
        }
        if (city.isBlank()){
            SnackBarManager.showMessage("please add your city")
            return
        }
        if (phoneNumber.isBlank()){
            SnackBarManager.showMessage("please add your phone number")
            return
        }
        if (address.isBlank()){
            SnackBarManager.showMessage("please add your full address")
            return
        }

        launchCatching(dispatcher = Dispatchers.IO) {
            repo.saveAddress(addressModel.value)
        }
        openScreen(ORDER_CONFIRMATION)
    }

    private fun getAddress() {
        launchCatching(dispatcher = Dispatchers.IO) {
            addressModel.value = repo.getAddress()
        }
    }

}