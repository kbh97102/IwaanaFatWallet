package arakene.fatwallet.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import arakene.fatwallet.R
import arakene.fatwallet.data.PayType
import arakene.fatwallet.databinding.PayAddLayoutBinding
import arakene.fatwallet.test.PayApplication
import arakene.fatwallet.test.TagBoxController
import arakene.fatwallet.viewModel.PayViewModel
import arakene.fatwallet.viewModel.PayViewModelFactory
import arakene.fatwallet.viewModel.TagViewModel
import arakene.fatwallet.viewModel.TagViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class NewFragment : Fragment() {

    private lateinit var binding: PayAddLayoutBinding
    private lateinit var tagBoxController: TagBoxController
    val model: PayViewModel by activityViewModels {
        PayViewModelFactory((requireActivity().application as PayApplication).payRepository)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.pay_add_layout, container, false)
        binding.vm = model

        tagBoxController = TagBoxController(binding.updateTags, binding.tagBox, this.context!!)

        setData()

        val bundle = arguments

        if (bundle != null) {
            binding.apply {
                updatePurpose.setText(bundle.getString("purpose"))
                updateDes.setText(bundle.getString("des"))
                updatePrice.setText(bundle.getString("price"))
                tagBoxController.addChipToAppliedTags(bundle.getString("tags"))
                when (bundle["type"] as PayType) {
                    PayType.input -> typeRadio.check(R.id.input)
                    PayType.output -> typeRadio.check(R.id.output)
                    else -> {}
                }
                updateDelete.visibility = View.VISIBLE
            }
        } else {
            binding.updateDelete.visibility = View.INVISIBLE
        }


        val tagViewModel: TagViewModel by activityViewModels {
            TagViewModelFactory((requireActivity().application as PayApplication).tagRepository)
        }

        tagViewModel.getTagList().observe(viewLifecycleOwner, {
            tagBoxController.totalTagList.apply {
                clear()
                addAll(it)
            }
            it.forEach { payTag ->
                tagBoxController.addButton(payTag.name, binding.tagBox)
            }
        })

        binding.updateTags.focusable = View.FOCUSABLE

        return binding.root
    }

    private fun setData() {
        binding.apply {
            updateDelete.setOnClickListener {
                model.deleteData()
                clear()
            }
            typeRadio.check(binding.input.id)
            newReset.setOnClickListener {
                clear()
            }
            pickedDate.text = getToday()
            updateOk.setOnClickListener {
                var selectedID = PayType.input
                when (typeRadio.checkedRadioButtonId) {
                    R.id.input -> selectedID = PayType.input
                    R.id.output -> selectedID = PayType.output
                }
                model.saveData(
                    selectedID,
                    updatePurpose.text.toString(),
                    updatePrice.text.toString(),
                    updateDes.text.toString(),
                    date = pickedDate.text.toString(),
                    tags = tagBoxController.getAppliedTags()
                )
                clear()
            }

            datePicker.setOnClickListener {
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
                        pickedDate.text = "$_year.$_month.$dayOfMonth ($dayName)"
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
        val date = calendar.time
        val simpledateformat = SimpleDateFormat("EEEE", Locale.getDefault())
        val dayName: String = simpledateformat.format(date)
        return "$year.$month.$day ($dayName)"
    }

    private fun clear() {
        binding.apply {
            pickedDate.text = getToday()
            updatePurpose.text = null
            updatePrice.text = null
            updateDes.text = null
            tagBoxController.clear()
        }
        val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.newReset.windowToken, 0)
    }
}