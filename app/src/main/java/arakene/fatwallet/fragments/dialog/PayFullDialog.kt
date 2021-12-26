package arakene.fatwallet.fragments.dialog

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import arakene.fatwallet.R
import arakene.fatwallet.data.PayDTO
import arakene.fatwallet.data.PayType
import arakene.fatwallet.databinding.PayFullDialogLayoutBinding
import arakene.fatwallet.viewModel.TagViewModel
import arakene.fatwallet.test.TestWordBox
import arakene.fatwallet.viewModel.PayViewModel
import java.text.SimpleDateFormat
import java.util.*

class PayFullDialog() : DialogFragment() {

    lateinit var binding: PayFullDialogLayoutBinding
    private val model: PayViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.pay_full_dialog_layout, container, false)

        binding.vm = model

        val testWordBox = TestWordBox(binding.updateTags, binding.wordBox, this.context!!)

        val tagViewModel: TagViewModel by activityViewModels()

        tagViewModel.getTagList().observe(viewLifecycleOwner, {
            it.forEach { payTag ->
                testWordBox.addButton(payTag.name)
            }
        })

        setClickListener()

        return binding.root
    }

    private fun setClickListener() {
        binding.apply {
            when (vm!!.getChangeTarget().value!!.type) {
                PayType.input -> typeRadio.check(R.id.input)
                PayType.output -> typeRadio.check(R.id.output)
                else -> {}
            }
            updateDelete.setOnClickListener {
                vm!!.deleteData()
                dismiss()
            }
            val builder = StringBuilder()
            vm!!.getChangeTarget().value!!.tags.forEachIndexed { index, payTag ->
                if (index == vm!!.getChangeTarget().value!!.tags.size - 1) {
                    builder.append(payTag.name)
                } else {
                    builder.append(payTag.name).append(" ")
                }
            }
            updateTags.text = builder.toString()

            updateOk.setOnClickListener {
                vm!!.updateData(
                    PayDTO(
//                        PayType.input,
//                        updatePurpose.text.toString(),
//                        updatePrice.text.toString().toLong(),
//                        updateDes.text.toString(),
//                        date = pickedDate.text.toString()
                    ), updateTags.text.toString()
                )
                this@PayFullDialog.dismiss()
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

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.BLACK))
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }

}