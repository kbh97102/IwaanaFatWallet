package arakene.fatwallet.recyclerView

import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginStart
import androidx.core.view.size
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import arakene.fatwallet.data.PayDTO
import arakene.fatwallet.databinding.PayListItemLayoutBinding
import arakene.fatwallet.fragments.dialog.PayFullDialog

class PayListHolder(private val binding: PayListItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    private lateinit var currentItem: PayDTO


    fun bind(item: PayDTO) {

        currentItem = item
        binding.apply {
            priceText.text = item.price.toString()
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