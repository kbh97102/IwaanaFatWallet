package arakene.fatwallet.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import arakene.fatwallet.R
import arakene.fatwallet.data.PayTag
import arakene.fatwallet.databinding.DefaultLayoutBinding
import arakene.fatwallet.recyclerView.monthly.MonthlyAdapter
import arakene.fatwallet.test.PayApplication
import arakene.fatwallet.viewModel.PayViewModel
import arakene.fatwallet.viewModel.PayViewModelFactory

class MainFragment : Fragment() {

    private lateinit var binding: DefaultLayoutBinding
    private val model: PayViewModel by activityViewModels {
        PayViewModelFactory((requireActivity().application as PayApplication).payRepository)
    }
    private val monthlyAdapter = MonthlyAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.default_layout, container, false)
        binding.lifecycleOwner = this
        binding.vm = model

        binding.monthlyOutRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = monthlyAdapter
        }

        model.currentMonthPays.observe(viewLifecycleOwner, {
            model.updateCurrentMonthPay()
        })

        model.test.observe(viewLifecycleOwner, {
            monthlyAdapter.apply {
                setItems(it)
                notifyDataSetChanged()
            }
        })


        return binding.root
    }


}