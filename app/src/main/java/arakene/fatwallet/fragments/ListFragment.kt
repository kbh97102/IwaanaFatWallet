package arakene.fatwallet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import arakene.fatwallet.R
import arakene.fatwallet.databinding.ListLayoutBinding
import arakene.fatwallet.recyclerView.PayListAdapter
import arakene.fatwallet.recyclerView.SwipeHelper
import arakene.fatwallet.viewModel.PayViewModel

class ListFragment : Fragment() {

    private lateinit var binding: ListLayoutBinding
    private lateinit var payAdapter: PayListAdapter
    private val model: PayViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.list_layout, container, false)
        initPayListView()

        model.getPayList().observe(this, {
            payAdapter.setItems(it)
            payAdapter.notifyDataSetChanged()
        })


        return binding.root
    }

    private fun initPayListView() {
        val swipeHelper = SwipeHelper().apply {
            setClamp(400f)
        }
        val itemTouchHelper = ItemTouchHelper(swipeHelper).apply {
            attachToRecyclerView(binding.payListRecyclerview)
        }
        payAdapter = PayListAdapter(model)
        binding.payListRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@ListFragment.context)
            adapter = payAdapter
            setOnTouchListener { _, _ ->
                swipeHelper.removePreviousClamp(this)
                false
            }
        }
    }
}