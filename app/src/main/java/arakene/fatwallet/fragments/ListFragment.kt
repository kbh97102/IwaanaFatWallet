package arakene.fatwallet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import arakene.fatwallet.R
import arakene.fatwallet.databinding.ListLayoutBinding
import arakene.fatwallet.recyclerView.PayListAdapter
import arakene.fatwallet.test.TagList
import arakene.fatwallet.viewModel.PayListViewModel
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

        model.getPayList().observe(viewLifecycleOwner, {
            payAdapter.setItems(it)
            payAdapter.notifyDataSetChanged()
        })

        val tagList: TagList by activityViewModels()
        val testList: PayListViewModel by activityViewModels()

        binding.listTag.setOnClickListener {
            binding.tagBox.visibility = View.VISIBLE
            binding.tagBox.removeAllViews()
            tagList.getTagList().value!!.forEach { payTag ->

                val button = Button(binding.tagBox.context).apply {
                    text = payTag.name
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(15, 15, 0, 0)
                    }
                    setOnClickListener {
                        binding.tagBox.visibility = View.INVISIBLE
                        payAdapter.setItems(testList.getSortedPaysByTag(payTag))
                        payAdapter.notifyDataSetChanged()
                    }
                }
                binding.tagBox.addView(button)

            }
        }

        return binding.root
    }

    private fun initPayListView() {
        payAdapter = PayListAdapter(model)
        binding.payListRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@ListFragment.context)
            adapter = payAdapter
        }
    }
}