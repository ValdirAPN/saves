package br.com.saves.network.di

import br.com.saves.network.BankAccountNetworkDataSource
import br.com.saves.network.BankAccountNetworkDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    fun providesBankAccountDataSource(): BankAccountNetworkDataSource {
        return BankAccountNetworkDataSourceImpl()
    }
}