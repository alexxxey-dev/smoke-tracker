package com.eatmybrain.smoketracker.ui.screens.premium

import androidx.lifecycle.*
import com.eatmybrain.smoketracker.data.BillingRepository
import com.eatmybrain.smoketracker.util.Constants
import com.eatmybrain.smoketracker.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class PremiumViewModel @Inject constructor(
    private val billingRepository: BillingRepository
) : ViewModel() {
    private val _price = MutableLiveData<Int>()
    val price:LiveData<Int> = _price

    val hasPremium = billingRepository.hasPremium().asLiveData()

    private val _purchaseError = MutableLiveData(false)
    val purchaseError :LiveData<Boolean> = _purchaseError

    private val _purchaseSuccess = MutableLiveData(false)
    val purchaseSuccess:LiveData<Boolean> = _purchaseSuccess
    init {
        loadPrice()
    }

    private fun loadPrice() = viewModelScope.launch{
        _price.value = withContext(Dispatchers.IO){
            billingRepository.price()
        }
    }


    fun purchase() = viewModelScope.launch{
        val successPurchase = withContext(Dispatchers.IO){billingRepository.purchase(Constants.PREMIUM_SKU)}
        if(successPurchase){
            _purchaseSuccess.value = true
        } else{
            _purchaseError.value = true
        }
    }

}