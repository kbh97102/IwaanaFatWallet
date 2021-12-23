package arakene.fatwallet.recyclerView

import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import arakene.fatwallet.R
import arakene.fatwallet.data.PayData
import arakene.fatwallet.data.PayType
import arakene.fatwallet.databinding.PayListItemLayoutBinding
import arakene.fatwallet.fragments.dialog.PayFullDialog

class PayListHolder(private val binding: PayListItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private lateinit var currentItem: PayData


    fun bind(item: PayData) {

        currentItem = item
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
                val textView = TextView(tags.context).apply {
                    text = it.name
                    textSize = 25f
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        setMargins(15, 0, 0, 0)
                    }
                }
                tags.addView(textView)
            }
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

}