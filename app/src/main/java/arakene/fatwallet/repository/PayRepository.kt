package arakene.fatwallet.repository

import androidx.annotation.WorkerThread
import arakene.fatwallet.dao.PayDao
import arakene.fatwallet.data.PayDTO
import arakene.fatwallet.data.PayTag
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

    fun getListWithDate(date: String): List<PayDTO> = payDao.getPaysWithDate(date)


    fun getPaysAfterDate(date: String): List<PayDTO> = payDao.getPaysAfterDate(date)

    fun test(tag: PayTag): List<PayDTO> = payDao.test2(tag)
}