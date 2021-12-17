package arakene.fatwallet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import arakene.fatwallet.R
import arakene.fatwallet.databinding.PayAddLayoutBinding

class NewFragment : Fragment() {

    private lateinit var binding : PayAddLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.pay_add_layout, container, false)
        return binding.root
    }

}