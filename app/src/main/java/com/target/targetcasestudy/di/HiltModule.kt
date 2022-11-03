package com.target.targetcasestudy.di

import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import com.target.targetcasestudy.BuildConfig
import com.target.targetcasestudy.api.BASE_URL
import com.target.targetcasestudy.network.DealsRepository
import com.target.targetcasestudy.network.DealsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {
    @Provides
    fun provideRetrofit(): Retrofit {
        val client: OkHttpClient by lazy {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
            val builder = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) {
                builder.addInterceptor(loggingInterceptor).addInterceptor(
                    OkHttpProfilerInterceptor())
            }
            builder.build()
        }
        return Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    fun provideDealsService() = DealsService(provideRetrofit())

    @Provides
    fun provideDealsRepository() = DealsRepository(provideDealsService())
}