package br.com.saves.data.di

import br.com.saves.data.repository.UserRepository
import br.com.saves.data.repository.UserRepositoryImpl
import br.com.saves.data.util.ConnectivityManagerNetworkMonitor
import br.com.saves.data.util.NetworkMonitor
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsUserRepository(
        userRepository: UserRepositoryImpl
    ): UserRepository

    @Binds
    fun bindsNetworkMonitor(
        networkMonitor: ConnectivityManagerNetworkMonitor,
    ): NetworkMonitor
}