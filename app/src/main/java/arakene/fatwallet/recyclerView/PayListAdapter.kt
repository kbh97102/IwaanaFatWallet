package arakene.fatwallet.recyclerView

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import arakene.fatwallet.databinding.PayListItemLayoutBinding
import arakene.fatwallet.dto.PayDTO
import arakene.fatwallet.viewModel.PayViewModel

class PayListAdapter(private val model: PayViewModel) : RecyclerView.Adapter<PayListHolder>() {

    private val items = ArrayList<PayDTO>()

    fun setItems(data: List<PayDTO>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayListHolder {
        val view = PayListItemLayoutBinding.inflate(LayoutInflater.from(parent.context))
        view.root.setOnClickListener {
            val map = hashMapOf(
                "type" to view.typeText.text.toString(),
                "purpose" to view.purposeText.text.toString(),
                "price" to view.priceText.text.toString(),
                "description" to view.desText.text.toString()
            )
            model.getChangeTarget().value = map
        }
        return PayListHolder(view)
    }

    override fun onBindViewHolder(holder: PayListHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}