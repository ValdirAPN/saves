package com.vpnt.saves.network.di

import com.vpnt.saves.network.ParseUserNetworkDatasource
import com.vpnt.saves.network.UserNetworkDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    fun providesUserNetworkDataSource(): UserNetworkDataSource = ParseUserNetworkDatasource()
}