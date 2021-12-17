package arakene.fatwallet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import arakene.fatwallet.R
import arakene.fatwallet.databinding.ListLayoutBinding

class ListFragment : Fragment() {

    private lateinit var binding: ListLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.list_layout, container, false)
        return binding.root
    }
}