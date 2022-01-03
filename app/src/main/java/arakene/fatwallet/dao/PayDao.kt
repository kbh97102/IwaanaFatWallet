package arakene.fatwallet.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import arakene.fatwallet.data.PayDTO
import arakene.fatwallet.data.PayTag
import kotlinx.coroutines.flow.Flow

@Dao
interface PayDao {

    @Query("SELECT * FROM pay_table")
    fun getAllPays(): Flow<List<PayDTO>>

    @Insert
    suspend fun insert(data: PayDTO)

    @Delete
    suspend fun delete(data: PayDTO)

    @Query("DELETE FROM pay_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM pay_table WHERE date = :date")
    fun getPaysWithDate(date: String): List<PayDTO>

    @Query("SELECT * FROM pay_table WHERE date >= :date")
    fun getPaysAfterDate(date: String): List<PayDTO>

    @Query("SELECT * FROM pay_table WHERE tags = :tag")
    fun test2(tag: PayTag): List<PayDTO>

    @Query("SELECT * FROM pay_table")
    fun test3(): List<PayDTO>
}