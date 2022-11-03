package com.target.targetcasestudy.network

import com.target.targetcasestudy.model.DealResponse
import com.target.targetcasestudy.model.DealsResponse
import retrofit2.http.Path
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DealsRepository @Inject constructor(val dealsService : DealsService) {
    //val dealsService = DealsService()
    suspend fun retrieveDeals() = dealsService.retrieveDeals()
    suspend fun retrieveDeal(@Path("dealId") dealId: String) = dealsService.retrieveDeal(dealId)
}
