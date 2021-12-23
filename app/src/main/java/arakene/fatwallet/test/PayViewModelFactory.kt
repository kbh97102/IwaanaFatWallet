package arakene.fatwallet.test

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import arakene.fatwallet.repository.PayRepository
import arakene.fatwallet.viewModel.PayViewModel

class PayViewModelFactory(private val repository: PayRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PayViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PayViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }


}