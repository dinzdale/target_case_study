package com.target.targetcasestudy.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.target.targetcasestudy.model.DealsResponse
import com.target.targetcasestudy.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DealsViewModel @Inject constructor(private val dealsRepository: DealsRepository) : ViewModel() {

    var selecedDealId = -1

    fun retrieveDeal(dealId: Int): LiveData<Result<Product>> {
        val result = MutableLiveData<Result<Product>>()
        viewModelScope.launch {
            result.postValue(dealsRepository.retrieveDeal(dealId))
        }
        return result
    }

    fun retrieveDeals(): LiveData<Result<DealsResponse>> {
        val result = MutableLiveData<Result<DealsResponse>>()
        viewModelScope.launch {
            result.postValue(dealsRepository.retrieveDeals())
        }
        return result
    }


}