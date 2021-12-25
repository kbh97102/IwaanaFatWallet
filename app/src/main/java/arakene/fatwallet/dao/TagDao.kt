package arakene.fatwallet.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import arakene.fatwallet.data.PayTag
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {

    @Query("SELECT * FROM tag_table")
    fun getAllTags(): Flow<List<PayTag>>

    @Insert
    suspend fun insert(data: PayTag)

    @Delete
    suspend fun delete(data: PayTag)

    @Query("DELETE FROM tag_table")
    suspend fun deleteAll()
}