package com.target.targetcasestudy.network

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.target.targetcasestudy.model.ProductResponse
import com.target.targetcasestudy.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DealsViewModel @Inject constructor(private val dealsRepository: DealsRepository) : ViewModel() {

    var selecedDealId = -1

    val productState = mutableStateOf<Result<Product>?>(null)
    fun retrieveDeal(dealId: Int): LiveData<Result<Product>> {
        val result = MutableLiveData<Result<Product>>()
        viewModelScope.launch {
            result.value = dealsRepository.retrieveDeal(dealId)
            productState.value = result.value
        }
        return result
    }

    fun retrieveDeals(): LiveData<Result<ProductResponse>> {
        val result = MutableLiveData<Result<ProductResponse>>()
        viewModelScope.launch {
            result.postValue(dealsRepository.retrieveDeals())
        }
        return result
    }


}