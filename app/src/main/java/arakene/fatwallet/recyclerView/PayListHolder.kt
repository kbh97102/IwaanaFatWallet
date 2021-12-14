package arakene.fatwallet.recyclerView

import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import arakene.fatwallet.databinding.PayListItemLayoutBinding
import arakene.fatwallet.databinding.PayUpdateDialogLayoutBinding
import arakene.fatwallet.dto.PayDTO
import arakene.fatwallet.dto.PayType

class PayListHolder(private val binding: PayListItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: PayDTO) {
        binding.apply {
            desText.text = item.description
            priceText.text = item.price.toString()
            purposeText.text = item.purpose.toString()
            typeText.text = item.type.toString()
        }
        val dialogBinding =
            PayUpdateDialogLayoutBinding.inflate(LayoutInflater.from(this@PayListHolder.binding.root.context))

        binding.testUpdate.setOnClickListener {

            val dialog = AlertDialog.Builder(this@PayListHolder.binding.root.context)
                .setView(dialogBinding.root)
                .create()

            var selectedType = item.type

            when (selectedType) {
                PayType.input -> dialogBinding.input.isChecked = true
                PayType.output -> dialogBinding.output.isChecked = true
            }

            dialogBinding.apply {
                updatePurpose.setText(item.purpose.toString())
                updatePrice.setText(item.price.toString())
                updateDes.setText(item.description.toString())

                typeRadio.setOnCheckedChangeListener { group, checkedId ->
                    when (checkedId) {
                        input.id -> selectedType = PayType.input
                        output.id -> selectedType = PayType.output
                    }
                }
                updateOk.setOnClickListener {
                    binding.vm!!.updateData(
                        PayDTO(
                            selectedType,
                            updatePurpose.text.toString(),
                            updatePrice.text.toString().toLong(),
                            updateDes.text.toString()
                        )
                    )
                    dialog.dismiss()
                }
                updateCancel.setOnClickListener {
                    dialog.dismiss()
                }
            }

            Log.e("DialogTest", "${dialogBinding.updatePurpose.text}")

            dialog.show()

        }
    }

    fun setTarget() {
        binding.vm!!.getChangeTarget().value = hashMapOf(
            "type" to binding.typeText.text.toString(),
            "purpose" to binding.purposeText.text.toString(),
            "price" to binding.priceText.text.toString(),
            "description" to binding.desText.text.toString()
        )
    }
}