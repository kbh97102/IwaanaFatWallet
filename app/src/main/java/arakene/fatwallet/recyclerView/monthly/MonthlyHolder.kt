package arakene.fatwallet.recyclerView.monthly

import androidx.recyclerview.widget.RecyclerView
import arakene.fatwallet.data.PayDTO
import arakene.fatwallet.databinding.MonthlyOutputItemLayoutBinding

class MonthlyHolder(private val binding : MonthlyOutputItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(payDTO: PayDTO) {
        binding.apply {
            monthlyPurpose.text = payDTO.purpose
            val priceText = "-${payDTO.price.toString()}"
            monthlyPrice.text = priceText
            monthlyDate.text = payDTO.date
        }
    }

}