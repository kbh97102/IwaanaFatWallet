package arakene.fatwallet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import arakene.fatwallet.R
import arakene.fatwallet.data.PayType
import arakene.fatwallet.databinding.PayAddLayoutBinding
import arakene.fatwallet.viewModel.PayViewModel

class NewFragment : Fragment() {

    private lateinit var binding: PayAddLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.pay_add_layout, container, false)

        val test : PayViewModel by activityViewModels()

        binding.vm = test

        binding.updateOk.setOnClickListener {
            var selectedID = PayType.input
            when (binding.typeRadio.checkedRadioButtonId) {
                R.id.input -> selectedID = PayType.input
                R.id.output -> selectedID = PayType.output
            }

            binding.apply {
                vm!!.saveData(
                    selectedID,
                    updatePurpose.text.toString(),
                    updatePrice.text.toString(),
                    updateDes.text.toString(),
                    pickedDate.text.toString(),
                    "tags"
                )
            }
        }

        return binding.root
    }


}