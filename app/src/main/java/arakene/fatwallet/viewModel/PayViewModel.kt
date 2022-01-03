package arakene.fatwallet.viewModel

import android.util.Log
import androidx.lifecycle.*
import arakene.fatwallet.data.PayDTO
import arakene.fatwallet.data.PayTag
import arakene.fatwallet.data.PayType
import arakene.fatwallet.recyclerView.PayListAdapter
import arakene.fatwallet.repository.PayRepository
import kotlinx.coroutines.*
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

    private val _specificDatePays = MutableLiveData<List<PayDTO>>()
    val specificDatePays: MutableLiveData<List<PayDTO>>
        get() = _specificDatePays

    private val inputText = MutableLiveData<String>()
    private val outputText = MutableLiveData<String>()

    fun getOutput(): MutableLiveData<String> = outputText

    fun getInput(): MutableLiveData<String> = inputText

    fun getPayList(): LiveData<List<PayDTO>> = list

    fun getChangeTarget(): MutableLiveData<PayDTO> = target

    private val testList = MutableLiveData<List<PayDTO>>()
    val currentMonthPays: MutableLiveData<List<PayDTO>>
        get() {
            CoroutineScope(Dispatchers.IO).launch {
                val calendar = Calendar.getInstance()
                val year = calendar[Calendar.YEAR]
                val month = calendar[Calendar.MONTH] + 1
                val result = repository.getPaysAfterDate("$year.$month")
                withContext(Dispatchers.Main) {
                    testList.value = result
                }
            }
            return testList
        }

    private val _test = MutableLiveData<List<PayDTO>>()
    val test: MutableLiveData<List<PayDTO>>
        get() {
            CoroutineScope(Dispatchers.IO).launch {
//                val result =
//                    repository.test(PayTag(name = PayTag.MONTHLYOUTPUT, count = 1))
//                withContext(Dispatchers.Main) {
//                    _test.value = result
//                    Log.e("ViewModelTest", result.toString())
//                }
                val result = repository.test2()
                val list = ArrayList<PayDTO>()
                result.forEach {
                    if (it.tags.contains(PayTag(name = PayTag.MONTHLYOUTPUT, count = 1))) {
                        list.add(it)
                    }
                }
                withContext(Dispatchers.Main){
                    _test.value = list
                }
            }
            return _test
        }


    fun setSortedPaysByDate(date: String, adapter: PayListAdapter) {
        CoroutineScope(Dispatchers.IO).launch {
            val result = repository.getListWithDate(date)
            withContext(Dispatchers.Main) {
                _specificDatePays.value = result
                adapter.apply {
                    setItems(result)
                    notifyDataSetChanged()
                }
            }
        }
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

    fun updateCurrentMonthPay() {
        var inputSum = 0L
        var outputSum = 0L

        testList.value?.let {
            it.forEach { pay ->
                if (pay.type == PayType.input) {
                    inputSum += pay.price!!
                } else {
                    outputSum += pay.price!!
                }
            }
        }
        inputText.value = inputSum.toString()
        outputText.value = outputSum.toString()
    }

}