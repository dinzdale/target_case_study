package com.target.targetcasestudy.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.target.targetcasestudy.model.DealResponse
import com.target.targetcasestudy.model.DealsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.http.Path
import javax.inject.Inject

@HiltViewModel
class DealsViewModel @Inject constructor(private val dealsRepository: DealsRepository) : ViewModel() {

    var selecedDealId = -1

    fun retrieveDeal(dealId: Int): LiveData<Result<DealResponse>> {
        val result = MutableLiveData<Result<DealResponse>>()
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