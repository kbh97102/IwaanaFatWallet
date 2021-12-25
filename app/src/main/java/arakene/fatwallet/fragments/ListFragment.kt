package arakene.fatwallet.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import arakene.fatwallet.R
import arakene.fatwallet.data.PayDTO
import arakene.fatwallet.data.PayTag
import arakene.fatwallet.databinding.ListLayoutBinding
import arakene.fatwallet.recyclerView.PayListAdapter
import arakene.fatwallet.test.PayApplication
import arakene.fatwallet.viewModel.TagViewModel
import arakene.fatwallet.viewModel.PayViewModel
import arakene.fatwallet.viewModel.PayViewModelFactory
import arakene.fatwallet.viewModel.TagViewModelFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ListFragment : Fragment() {

    private lateinit var binding: ListLayoutBinding
    private lateinit var payAdapter: PayListAdapter
    private val model: PayViewModel by activityViewModels{
        PayViewModelFactory((requireActivity().application as PayApplication).payRepository)
    }
    private val tagViewModel: TagViewModel by activityViewModels{
        TagViewModelFactory((requireActivity().application as PayApplication).tagRepository)
    }


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

        setUI()

        setTagSpinner()

        return binding.root
    }

    private fun setTagSpinner(){
        tagViewModel.getTagList().observe(viewLifecycleOwner, { it ->
            val list = ArrayList<String>().apply {
                add("Tag")
                it.forEach {
                    this.add(it.name)
                }
            }
            binding.listTag.adapter = ArrayAdapter<String>(
                requireContext(),
                R.layout.support_simple_spinner_dropdown_item,
                list
            )
            binding.listTag.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val tagName = binding.listTag.getItemAtPosition(position)
                    if (tagName == "Tag") {
                        return
                    }
                    it.forEach { payTag ->
                        if (payTag.name == tagName) {
                            setSortedData(payTag)
                            return@forEach
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
        })
    }

    private fun setUI() {
        binding.apply {
            listReset.setOnClickListener {
                setAdapterData(model.getPayList().value!!)
            }
            listPickedDate.text = getToday()
            listDate.setOnClickListener {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)
                val datePickerDialog = DatePickerDialog(
                    requireContext(),
                    { _, _year, monthOfYear, dayOfMonth ->
                        val _month = monthOfYear + 1
                        val _calendar = Calendar.getInstance()
                        _calendar.set(year, monthOfYear, dayOfMonth)
                        val date = calendar.time
                        val simpledateformat = SimpleDateFormat("EEEE", Locale.getDefault())
                        val dayName: String = simpledateformat.format(date)
                        val dayText = "$_year.$_month.$dayOfMonth ($dayName)"
                        setAdapterData(model.getSortedPaysByDate(dayText))
                        binding.listPickedDate.text = "$_year.$_month.$dayOfMonth"
                    },
                    year,
                    month,
                    day
                )
                datePickerDialog.show()
            }
        }
    }

    private fun getToday(): String {
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH] + 1
        val day = calendar[Calendar.DAY_OF_MONTH]
        return "$year.$month.$day"
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