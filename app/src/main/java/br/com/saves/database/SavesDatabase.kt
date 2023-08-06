package br.com.saves.database

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.saves.database.dao.BankAccountDao
import br.com.saves.database.dao.CreditCardDao
import br.com.saves.database.dao.TransactionDao
import br.com.saves.database.model.BankAccountEntity
import br.com.saves.database.model.CreditCardEntity
import br.com.saves.database.model.TransactionEntity

@Database(
    entities =[
        CreditCardEntity::class,
        BankAccountEntity::class,
        TransactionEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class SavesDatabase : RoomDatabase() {

    abstract fun bankAccountDao(): BankAccountDao
    abstract fun creditCardDao(): CreditCardDao
    abstract fun transactionDao(): TransactionDao
}