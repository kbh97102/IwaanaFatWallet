package arakene.fatwallet.recyclerView

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import arakene.fatwallet.databinding.PayListItemLayoutBinding
import arakene.fatwallet.dto.PayDTO

class PayListHolder(private val binding: PayListItemLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: PayDTO) {
        binding.apply {
            desText.text = item.description
            priceText.text = item.price.toString()
            purposeText.text = item.purpose.toString()
            typeText.text = item.type.toString()
        }
    }
}