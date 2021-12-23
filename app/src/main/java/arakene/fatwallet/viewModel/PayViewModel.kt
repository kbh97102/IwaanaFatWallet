package arakene.fatwallet.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import arakene.fatwallet.data.PayData
import arakene.fatwallet.data.PayTag
import arakene.fatwallet.data.PayType
import arakene.fatwallet.repository.PayRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PayViewModel(private val repository: PayRepository) : ViewModel() {


    private val list: LiveData<List<PayData>> = repository.allPays.asLiveData()
    private val target: MutableLiveData<PayData> by lazy {
        MutableLiveData()
    }

    fun getPayList(): LiveData<List<PayData>> = list

    fun getChangeTarget(): MutableLiveData<PayData> = target

    fun saveData(
        type: PayType,
        purpose: String,
        price: String,
        des: String,
        date: String,
        tags: String
    ) {

        CoroutineScope(Dispatchers.IO).launch {
            val tagList = ArrayList<PayTag>()
            tags.split(" ").onEach { name ->
                if (name.trim() != "") {
                    tagList.add(PayTag(name, 1))
                }
            }

            repository.insert(
                PayData(
                    type = type,
                    date = date,
                    description = des,
                    price = price.toLong(),
                    purpose = purpose,
                    tags = tagList
                )
            )
        }

    }

    fun deleteData() {

    }

    fun updateData(data: PayData, tags: String) {
    }

}