package arakene.fatwallet.repository

import androidx.annotation.WorkerThread
import arakene.fatwallet.dao.PayDao
import arakene.fatwallet.data.PayData
import kotlinx.coroutines.flow.Flow

class PayRepository(private val payDao: PayDao) {

    val allPays : Flow<List<PayData>> = payDao.getAllPays()

    @WorkerThread
    suspend fun insert(pay: PayData) {
        payDao.insert(pay)
    }
}