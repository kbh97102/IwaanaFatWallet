package arakene.fatwallet.recyclerView

import android.app.DatePickerDialog
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import arakene.fatwallet.data.PayDTO
import arakene.fatwallet.data.PayType
import arakene.fatwallet.databinding.PayAddLayoutBinding
import arakene.fatwallet.databinding.PayListItemLayoutBinding
import arakene.fatwallet.fragments.PayFullDialog
import java.text.SimpleDateFormat
import java.util.*

class PayListHolder(private val binding: PayListItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private lateinit var currentItem: PayDTO


    fun bind(item: PayDTO) {
        currentItem = item
        binding.apply {
            desText.text = item.description
            priceText.text = item.price.toString()
            purposeText.text = item.purpose.toString()
            typeText.text = item.type.toString()
            tags.text = item.tags.toString()
            date.text = item.date.toString()
        }

        binding.root.setOnClickListener {
            binding.vm!!.getChangeTarget().value = item
            PayFullDialog().show(
                ((binding.root.context) as FragmentActivity).supportFragmentManager,
                null
            )
        }
    }

    fun setTarget() {
        binding.vm!!.getChangeTarget().value = currentItem
    }

    private fun setDialog(item: PayDTO) {
        val dialogBinding =
            PayAddLayoutBinding.inflate(LayoutInflater.from(this@PayListHolder.binding.root.context))

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
                        item.apply {
                            this.type = selectedType
                            this.purpose = updatePurpose.text.toString()
                            this.price = updatePrice.text.toString().toLong()
                            this.description = updateDes.text.toString()
                            this.date = pickedDate.text.toString()
                            //TODO Tag 지정하는거 문제네이거
                        }
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

}