package arakene.fatwallet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import arakene.fatwallet.R
import arakene.fatwallet.data.PayDTO
import arakene.fatwallet.data.PayTag
import arakene.fatwallet.databinding.ListLayoutBinding
import arakene.fatwallet.recyclerView.PayListAdapter
import arakene.fatwallet.test.TagList
import arakene.fatwallet.viewModel.PayViewModel

class ListFragment : Fragment() {

    private lateinit var binding: ListLayoutBinding
    private lateinit var payAdapter: PayListAdapter
    private val model: PayViewModel by activityViewModels()
    private val tagList: TagList by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.list_layout, container, false)
        initPayListView()

        model.getPayList().observe(viewLifecycleOwner, {
            setAdapterData(it)
        })

        binding.listReset.setOnClickListener {
            setAdapterData(model.getPayList().value!!)
        }

        tagList.getTagList().observe(viewLifecycleOwner, { it ->
            val list = ArrayList<String>().apply {
                it.forEach {
                    this.add(it.name)
                }
            }
            binding.listTag.adapter = ArrayAdapter<String>(requireContext(), R.layout.support_simple_spinner_dropdown_item, list)
            binding.listTag.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val tagName = binding.listTag.getItemAtPosition(position)
                    it.forEach { payTag ->
                        if (payTag.name == tagName) {
                            setSortedData(payTag)
                            return@forEach
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    setAdapterData(model.getPayList().value!!)
                }
            }
        })


        return binding.root
    }

    private fun setSortedData(tag: PayTag) {
        setAdapterData(model.getSortedPaysByTag(tag))
    }

    private fun setAdapterData(list: List<PayDTO>) {
        payAdapter.setItems(list)
        payAdapter.notifyDataSetChanged()
    }

    private fun initPayListView() {
        payAdapter = PayListAdapter(model)
        binding.payListRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@ListFragment.context)
            adapter = payAdapter
        }
    }
}