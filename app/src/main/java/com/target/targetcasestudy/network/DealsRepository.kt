package com.target.targetcasestudy.network

import retrofit2.http.Path
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DealsRepository @Inject constructor(val dealsService : DealsService) {
    suspend fun retrieveDeals() = dealsService.retrieveDeals()
    suspend fun retrieveDeal(dealId: Int) = dealsService.retrieveDeal(dealId)
}
