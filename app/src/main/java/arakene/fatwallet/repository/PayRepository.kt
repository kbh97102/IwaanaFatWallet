package arakene.fatwallet.repository

import androidx.annotation.WorkerThread
import arakene.fatwallet.dao.PayDao
import arakene.fatwallet.data.PayDTO
import kotlinx.coroutines.flow.Flow

class PayRepository(private val payDao: PayDao) {

    val allPays: Flow<List<PayDTO>> = payDao.getAllPays()

    @WorkerThread
    suspend fun insert(pay: PayDTO) {
        payDao.insert(pay)
    }

    suspend fun delete(pay: PayDTO) {
        payDao.delete(pay)
    }
}