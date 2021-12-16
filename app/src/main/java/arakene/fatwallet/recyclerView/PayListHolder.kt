package arakene.fatwallet.recyclerView

import android.app.DatePickerDialog
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import arakene.fatwallet.data.PayDTO
import arakene.fatwallet.data.PayType
import arakene.fatwallet.databinding.PayListItemLayoutBinding
import arakene.fatwallet.databinding.PayUpdateDialogLayoutBinding
import java.text.SimpleDateFormat
import java.util.*

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

                datePicker.setOnClickListener {
                    val calendar = Calendar.getInstance()
                    val year = calendar[Calendar.YEAR]
                    val month = calendar[Calendar.MONTH]
                    val day = calendar[Calendar.DAY_OF_MONTH]

                    val datePickerDialog = DatePickerDialog(
                        binding.root.context,
                        { _, _year, monthOfYear, dayOfMonth ->
                            val selectedMonth = monthOfYear + 1
                            val date = calendar.time
                            val simpledateformat = SimpleDateFormat("EEEE", Locale.getDefault())
                            val dayName: String = simpledateformat.format(date)
                            pickedDate.text = "$_year.$selectedMonth.$dayOfMonth ($dayName)"
                        }, year, month, day
                    )
                    datePickerDialog.show()
                }

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