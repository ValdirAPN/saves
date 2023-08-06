package br.com.saves.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import br.com.saves.database.model.BankAccountEntity
import br.com.saves.database.model.CreditCardEntity
import br.com.saves.database.model.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query(value = "SELECT * FROM transactions")
    fun getEntities(): Flow<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnore(entity: TransactionEntity): Long

    @Upsert
    suspend fun upsert(entity: TransactionEntity)

    @Query(
        value = """
            DELETE FROM transactions
            WHERE id=:id
        """
    )
    suspend fun delete(id: String)
}