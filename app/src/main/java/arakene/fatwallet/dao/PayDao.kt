package arakene.fatwallet.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import arakene.fatwallet.data.PayData
import kotlinx.coroutines.flow.Flow

@Dao
interface PayDao {

    @Query("SELECT * FROM pay_table")
    fun getAllPays(): Flow<List<PayData>>

    @Insert
    suspend fun insert(data: PayData)

    @Delete
    suspend fun delete(data: PayData)

    @Query("DELETE FROM pay_table")
    suspend fun deleteAll()
}