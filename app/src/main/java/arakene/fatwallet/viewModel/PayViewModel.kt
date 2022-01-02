package arakene.fatwallet.viewModel

import androidx.lifecycle.*
import arakene.fatwallet.data.PayDTO
import arakene.fatwallet.data.PayTag
import arakene.fatwallet.data.PayType
import arakene.fatwallet.repository.PayRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class PayViewModel(private val repository: PayRepository) : ViewModel() {


    private val list: LiveData<List<PayDTO>> = repository.allPays.asLiveData()
    private val target: MutableLiveData<PayDTO> by lazy {
        MutableLiveData()
    }

    private val monthlyList: MutableLiveData<List<PayDTO>> by lazy {
        MutableLiveData()
    }

    fun getMonthlyOutputList(): MutableLiveData<List<PayDTO>> {
        monthlyList.value = getSortedPaysByTag(PayTag(name = PayTag.MONTHLYOUTPUT, count = 1))
        return monthlyList
    }

    private val inputText = MutableLiveData<String>()
    private val outputText = MutableLiveData<String>()

    init {
        getCurrentMonthPay()
    }

    fun getOutput(): MutableLiveData<String> = outputText

    fun getInput(): MutableLiveData<String> = inputText

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
        list.value?.let {
            it.forEach { pay ->
                if (pay.tags.contains(tag)) {
                    sortedList.add(pay)
                }
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
        if (price == "") {
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            val tagList = ArrayList<PayTag>()
            tags.split(" ").onEach { name ->
                if (name.trim() != "") {
                    tagList.add(PayTag(name = name, count = 1))
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
                tagList.add(PayTag(name = name, count = 1))
            }
        }
        data.tags = tagList
    }

    fun getCurrentMonthPay(): ArrayList<PayDTO> {
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH] + 1
        var inputSum = 0L
        var outputSum = 0L

        val sortedList = getSortedPaysByTag(PayTag(name = PayTag.MONTHLYOUTPUT, count = 1))

        sortedList.forEach { pay ->
            if (pay.date!!.startsWith("$year.$month")) {
                if (pay.type == PayType.input) {
                    inputSum += pay.price!!
                } else {
                    outputSum += pay.price!!
                }
            }
        }
        inputText.value = inputSum.toString()
        outputText.value = outputSum.toString()

        return sortedList
    }

}