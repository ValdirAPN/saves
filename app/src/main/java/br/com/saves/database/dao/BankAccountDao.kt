package br.com.saves.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import br.com.saves.database.model.BankAccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BankAccountDao {

    @Query(value = "SELECT * FROM bank_accounts")
    fun getEntities(): Flow<List<BankAccountEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnore(entity: BankAccountEntity): Long

    @Upsert
    suspend fun upsert(entity: BankAccountEntity)

    @Upsert
    suspend fun upsert(entities: List<BankAccountEntity>)

    @Query(
        value = """
            DELETE FROM bank_accounts
            WHERE id=:id
        """
    )
    suspend fun delete(id: String)
}