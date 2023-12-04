package com.portal.weatherapp.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.portal.weatherapp.data.WeatherService
import com.portal.weatherapp.repository.db.location.LocationDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideLocationDatabase(application: Application): LocationDatabase {
        return Room.databaseBuilder(
            application,
            LocationDatabase::class.java,
            "location_database"
        ).allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideApiService(
        okHttpClient: OkHttpClient,
        retrofit: Retrofit.Builder
    ): WeatherService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(WeatherService::class.java)

}