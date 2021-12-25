package arakene.fatwallet.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import arakene.fatwallet.repository.TagRepository

class TagViewModelFactory(private val repository: TagRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TagViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TagViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}