package arakene.fatwallet.viewModel

import androidx.lifecycle.*
import arakene.fatwallet.data.PayDTO
import arakene.fatwallet.data.PayTag
import arakene.fatwallet.data.PayType
import arakene.fatwallet.repository.PayRepository
import arakene.fatwallet.test.PayApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PayViewModel(private val repository: PayRepository) : ViewModel() {


    private val list: LiveData<List<PayDTO>> = repository.allPays.asLiveData()
    private val target: MutableLiveData<PayDTO> by lazy {
        MutableLiveData()
    }

    private val monthlyList: MutableLiveData<List<PayDTO>> by lazy {
        MutableLiveData()
    }

    fun getMonthlyOutputList(): MutableLiveData<List<PayDTO>> {
        monthlyList.value = getSortedPaysByTag(PayTag(PayTag.MONTHLYOUTPUT, 1))
        return monthlyList
    }

    fun getPayList(): LiveData<List<PayDTO>> = list

    fun getChangeTarget(): MutableLiveData<PayDTO> = target

    fun getSortedPaysByDate(date: String): ArrayList<PayDTO> {
        val sortedList = ArrayList<PayDTO>()
        list.value!!.forEach {
            if (it.date == date) {
                sortedList.add(it)
            }
        }
        return sortedList
    }

    fun getSortedPaysByTag(tag: PayTag): ArrayList<PayDTO> {
        val sortedList = ArrayList<PayDTO>()
        list.value!!.forEach {
            if (it.tags.contains(tag)) {
                sortedList.add(it)
            }
        }
        return sortedList
    }

    fun saveData(
        type: PayType,
        purpose: String,
        price: String,
        des: String,
        date: String,
        tags: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val tagList = ArrayList<PayTag>()
            tags.split(" ").onEach { name ->
                if (name.trim() != "") {
                    tagList.add(PayTag(name, 1))
                }
            }

            repository.insert(
                PayDTO(
                    type = type,
                    purpose = purpose,
                    description = des,
                    price = price.toLong(),
                    date = date,
                    tags = tagList
                )
            )
        }
    }

    fun deleteData() {
        viewModelScope.launch(Dispatchers.IO) {
            if (target.value == null) {
                return@launch
            }
            repository.delete(target.value!!)
        }
    }

    fun updateData(data: PayDTO, tags: String) {
        val tagList = ArrayList<PayTag>()
        tags.split(" ").let {
            it.forEach { name ->
                tagList.add(PayTag(name, 1))
            }
        }

        data.tags = tagList


    }

}