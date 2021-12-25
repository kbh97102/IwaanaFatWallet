package arakene.fatwallet.viewModel

import androidx.lifecycle.*
import arakene.fatwallet.data.PayTag
import arakene.fatwallet.repository.TagRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TagViewModel(private val repository: TagRepository) : ViewModel() {

    private val target: MutableLiveData<PayTag> by lazy {
        MutableLiveData()
    }

    private val list: LiveData<List<PayTag>> = repository.allPays.asLiveData()

    fun getTagList(): LiveData<List<PayTag>> = list

    fun setTarget(item: PayTag) {
        target.value = item
    }

    private fun isContains(tagName: String): Boolean {
        list.value!!.forEach {
            if (it.name == tagName) {
                return true
            }
        }
        return false
    }

    fun updateTag(tagName: String) {

    }

    fun addTag(tagName: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(PayTag(name = tagName, count = 1))
    }

    fun deleteTag(removeTarget: PayTag) = viewModelScope.launch(Dispatchers.IO) {
        if (list.value!!.contains(removeTarget)) {
            repository.delete(removeTarget)
        }
    }
}