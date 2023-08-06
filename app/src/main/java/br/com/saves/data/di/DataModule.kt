package br.com.saves.data.di

import br.com.saves.data.repository.BankAccountRepository
import br.com.saves.data.repository.CreditCardRepository
import br.com.saves.data.repository.OfflineFirstBankAccountRepository
import br.com.saves.data.repository.OfflineFirstCreditCardRepository
import br.com.saves.data.repository.OfflineFirstTransactionRepository
import br.com.saves.data.repository.TransactionRepository
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

    @Binds
    fun bindsCreditCardRepository(
        creditCardRepository: OfflineFirstCreditCardRepository
    ): CreditCardRepository

    @Binds
    fun bindsBankAccountRepository(
        bankAccountRepository: OfflineFirstBankAccountRepository
    ): BankAccountRepository

    @Binds
    fun bindsTransactionRepository(
        transactionRepository: OfflineFirstTransactionRepository
    ): TransactionRepository
}