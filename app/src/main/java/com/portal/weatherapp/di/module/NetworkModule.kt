package com.portal.weatherapp.di.module

import android.app.Application
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.GsonBuilder
import com.portal.weatherapp.data.WeatherService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }


    @Provides
    @Singleton
    fun provideAuthenticationOkhttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        application: Application,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(45L, TimeUnit.SECONDS)
            .writeTimeout(45L, TimeUnit.SECONDS)
            .readTimeout(45L, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(
                ChuckerInterceptor.Builder(application.applicationContext)
                    .collector(ChuckerCollector(application.applicationContext))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
            ).addNetworkInterceptor(Interceptor { chain ->
                val original: Request = chain.request()
                val removeHeader = original.headers["removeHeader"]
                val shouldAddAuthHeaders: Boolean = removeHeader.isNullOrEmpty()
                val requestBuilder: Request.Builder = chain.request().newBuilder()
                if (shouldAddAuthHeaders) {
                    requestBuilder.header("Content-Type", "application/json; charset=utf-8")
                }
                chain.proceed(requestBuilder.build())
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideBaseUrl() = WeatherService.BASE_URL

    @Provides
    @Singleton
    fun provideRetrofitBuilder(baseUrl: String): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().serializeNulls().create()
            )
        )
    }

}