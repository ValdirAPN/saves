package br.com.saves.database.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.saves.database.SavesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesSavesDatabase(
        @ApplicationContext context: Context
    ): SavesDatabase {
        val updateBalanceIncome = """
            CREATE TRIGGER IF NOT EXISTS update_balance_income
            AFTER INSERT ON transactions
            FOR EACH ROW
            WHEN NEW.type = 'INCOME'
            BEGIN
                UPDATE bank_accounts
                SET balance = balance + NEW.amount
                WHERE id = NEW.bankAccountId;
            END;
        """.trimIndent()

        val updateBalanceExpense = """
            CREATE TRIGGER IF NOT EXISTS update_balance_expense
            AFTER INSERT ON transactions
            FOR EACH ROW
            WHEN NEW.type = 'EXPENSE'
            BEGIN
                UPDATE bank_accounts
                SET balance = balance - NEW.amount
                WHERE id = NEW.bankAccountId;
                
                UPDATE credit_cards
                SET availableLimit = availableLimit - NEW.amount
                WHERE id = NEW.creditCardId;
            END;
        """.trimIndent()

        val database = Room
            .databaseBuilder(
                context,
                SavesDatabase::class.java,
                "saves-database"
            )
            .build()

        database.openHelper.writableDatabase.apply {
            execSQL(updateBalanceExpense)
            execSQL(updateBalanceIncome)
        }

        return database
    }
}