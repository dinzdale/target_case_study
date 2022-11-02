package com.target.targetcasestudy.network

import android.content.Context

import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import com.target.targetcasestudy.BuildConfig
import com.target.targetcasestudy.api.BASE_URL
import com.target.targetcasestudy.api.DealApiKtx
import com.target.targetcasestudy.model.DealsResponse
import com.target.targetcasestudy.model.DealResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Path


//class DealsService(val context: Context)
class DealsService(){

    private val tag = DealsService::class.java.simpleName

    //private val domain = context.getString(R.string.schema)
    val BASE_URL = "https://api.target.com/mobile_case_study_deals/v1/"
    private val client: OkHttpClient by lazy {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(loggingInterceptor).addInterceptor(OkHttpProfilerInterceptor())
        }
        builder.build()
    }
    private val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL).client(client)
        .addConverterFactory(GsonConverterFactory.create()).build()
    private val apiService: DealApiKtx = retrofit.create(DealApiKtx::class.java)
//    private val headerMap = hashMapOf<String, String>(
//        //"User-Agent" to context.applicationInfo.packageName,
//        //"X-API-Key" to context.getString(R.string.api_key)
//    )


    suspend fun retrieveDeals(): Result<DealsResponse> {
        return try {
            Result.success(apiService.retrieveDeals())
        } catch (ex: Exception) {
            Result.failure(ex)
        }

    }


    suspend fun retrieveDeal(@Path("dealId") dealId: String): Result<DealResponse> {
        return try {
            Result.success(apiService.retrieveDeal(dealId))
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }
}

