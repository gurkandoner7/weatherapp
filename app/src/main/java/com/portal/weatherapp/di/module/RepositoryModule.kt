package com.portal.weatherapp.di.module

import com.portal.weatherapp.domain.repository.WeatherRepository
import com.portal.weatherapp.domain.repository.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun provideWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository
}
