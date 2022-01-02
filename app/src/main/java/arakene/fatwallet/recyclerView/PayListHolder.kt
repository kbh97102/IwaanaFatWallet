package arakene.fatwallet.recyclerView

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import arakene.fatwallet.R
import arakene.fatwallet.data.PayDTO
import arakene.fatwallet.data.PayType
import arakene.fatwallet.databinding.PayListItemLayoutBinding
import com.google.android.material.chip.Chip
import java.lang.StringBuilder

class PayListHolder(private val binding: PayListItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: PayDTO) {
        binding.apply {
            when (item.type) {
                PayType.input -> {
                    priceText.apply {
                        text = "+${item.price.toString()}"
                        setTextColor(ContextCompat.getColor(context, R.color.input_color))
                    }
                }
                PayType.output -> {
                    priceText.apply {
                        text = "-${item.price.toString()}"
                        setTextColor(ContextCompat.getColor(context, R.color.output_color))
                    }
                }
            }

            purposeText.text = item.purpose.toString()
            tags.removeAllViews()
            item.tags.forEach {
                val chip = Chip(binding.root.context).apply {
                    text = it.name
                    isCloseIconVisible = false
                    layoutDirection = View.LAYOUT_DIRECTION_LOCALE
                }
                tags.addView(chip)
            }
            date.text = item.date.toString()
        }

        binding.root.setOnClickListener {
            binding.vm!!.getChangeTarget().value = item
            val builder = StringBuilder()

            item.tags.forEachIndexed { index, payTag ->
                if (index == item.tags.size - 1) {
                    builder.append(payTag.name)
                }else{
                    builder.append(payTag.name).append(" ")
                }
            }

            val bundle = bundleOf(
                "purpose" to item.purpose,
                "price" to item.price.toString(),
                "des" to item.description,
                "date" to item.date,
                "type" to item.type,
                "tags" to builder.toString()
            )

            it.findNavController().navigate(R.id.newFragment, bundle)
        }
    }

}