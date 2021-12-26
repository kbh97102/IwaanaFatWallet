package arakene.fatwallet.fragments

import android.os.Bundle
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
import arakene.fatwallet.viewModel.PayViewModel

class MainFragment : Fragment() {

    private lateinit var binding: DefaultLayoutBinding
    private val model: PayViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.default_layout, container, false)
        binding.lifecycleOwner = this
        binding.vm = model

        val monthlyAdapter = MonthlyAdapter()

        binding.monthlyOutRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = monthlyAdapter
        }

        model.getPayList().observe(viewLifecycleOwner, {
            monthlyAdapter.apply {
                setData(model.getCurrentMonthPay())
                notifyDataSetChanged()
            }
        })

        return binding.root
    }

}