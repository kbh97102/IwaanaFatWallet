package arakene.fatwallet.repository

import androidx.annotation.WorkerThread
import arakene.fatwallet.dao.TagDao
import arakene.fatwallet.data.PayTag
import kotlinx.coroutines.flow.Flow

class TagRepository(private val tagDao: TagDao) {

    val allPays: Flow<List<PayTag>> = tagDao.getAllTags()

    @WorkerThread
    suspend fun insert(tag: PayTag) {
        tagDao.insert(tag)
    }

    suspend fun delete(tag: PayTag) {
        tagDao.delete(tag)
    }

}