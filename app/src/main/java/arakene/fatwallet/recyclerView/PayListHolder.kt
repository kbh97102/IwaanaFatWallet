package arakene.fatwallet.recyclerView

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

    fun setTarget() {
        binding.vm!!.getChangeTarget().value = hashMapOf(
            "type" to binding.typeText.text.toString(),
            "purpose" to binding.purposeText.text.toString(),
            "price" to binding.priceText.text.toString(),
            "description" to binding.desText.text.toString()
        )
    }
}