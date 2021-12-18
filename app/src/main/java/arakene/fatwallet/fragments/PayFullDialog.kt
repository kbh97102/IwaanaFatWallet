package arakene.fatwallet.fragments

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
import arakene.fatwallet.databinding.PayAddLayoutBinding
import arakene.fatwallet.databinding.PayFullDialogLayoutBinding
import arakene.fatwallet.viewModel.PayViewModel

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

        binding.updateCancel.setOnClickListener {
            dismiss()
        }

        binding.apply {
            when(vm!!.getChangeTarget().value!!.type){
                PayType.input -> typeRadio.check(R.id.input)
                PayType.output -> typeRadio.check(R.id.output)
                else -> {}
            }

            updateOk.setOnClickListener {
                vm!!.updateData(
                    PayDTO(
                        PayType.input,
                        updatePurpose.text.toString(),
                        updatePrice.text.toString().toLong(),
                        updateDes.text.toString(),
                        date = pickedDate.text.toString()
                    )
                )
                this@PayFullDialog.dismiss()
            }
        }

        return binding.root
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