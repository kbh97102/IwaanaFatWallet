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
import arakene.fatwallet.databinding.DefaultLayoutBinding
import arakene.fatwallet.recyclerView.monthly.MonthlyAdapter
import arakene.fatwallet.viewModel.PayListViewModel
import arakene.fatwallet.viewModel.PayViewModel

class MainFragment : Fragment() {

    private lateinit var binding: DefaultLayoutBinding
    private val model: PayListViewModel by activityViewModels()
    private val model2: PayViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.default_layout, container, false)
        binding.lifecycleOwner = this
        binding.payListViewModel = model

        val monthlyAdapter = MonthlyAdapter()

        binding.monthlyOutRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = monthlyAdapter
        }

//        model2.getMonthlyOutputList().observe(viewLifecycleOwner, {
//            monthlyAdapter.apply {
//                setData(it)
//                notifyDataSetChanged()
//            }
//        })

        return binding.root
    }

}