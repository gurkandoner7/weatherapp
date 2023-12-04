package com.portal.weatherapp.di.module

import com.portal.weatherapp.domain.usecase.LocationDbUseCase
import com.portal.weatherapp.domain.usecase.LocationDbUseCaseImpl
import com.portal.weatherapp.domain.usecase.WeatherUseCase
import com.portal.weatherapp.domain.usecase.WeatherUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun provideWeatherUseCase(weatherUseCaseImpl: WeatherUseCaseImpl): WeatherUseCase

    @Binds
    abstract fun provideLocationDbUseCase(locationDbUseCaseImpl: LocationDbUseCaseImpl): LocationDbUseCase
}