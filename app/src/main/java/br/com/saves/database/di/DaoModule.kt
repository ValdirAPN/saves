package br.com.saves.database.di

import br.com.saves.database.SavesDatabase
import br.com.saves.database.dao.BankAccountDao
import br.com.saves.database.dao.CreditCardDao
import br.com.saves.database.dao.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun providesBankAccountDao(
        database: SavesDatabase
    ): BankAccountDao = database.bankAccountDao()

    @Provides
    fun providesCreditCardDao(
        database: SavesDatabase
    ): CreditCardDao = database.creditCardDao()

    @Provides
    fun providesTransactionDao(
        database: SavesDatabase
    ): TransactionDao = database.transactionDao()
}