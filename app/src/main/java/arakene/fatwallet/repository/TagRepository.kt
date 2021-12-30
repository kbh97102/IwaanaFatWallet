package arakene.fatwallet.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.asLiveData
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

    suspend fun delete(name: String) {
        allPays.asLiveData().value?.let {
            it.forEach { tag ->
                if (tag.name == name) {
                    tagDao.delete(tag)
                    return@forEach
                }
            }
        }
    }
}