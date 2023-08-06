package br.com.saves.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import br.com.saves.database.model.BankAccountEntity
import br.com.saves.database.model.CreditCardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CreditCardDao {

    @Query(value = "SELECT * FROM credit_cards")
    fun getEntities(): Flow<List<CreditCardEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnore(entity: CreditCardEntity): Long

    @Upsert
    suspend fun upsert(entity: CreditCardEntity)

    @Query(
        value = """
            DELETE FROM credit_cards
            WHERE id=:id
        """
    )
    suspend fun delete(id: String)
}