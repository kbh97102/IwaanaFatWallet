package arakene.fatwallet.recyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import arakene.fatwallet.databinding.PayListItemLayoutBinding
import arakene.fatwallet.dto.PayDTO

class PayListAdapter : RecyclerView.Adapter<PayListHolder>() {

    private val items = ArrayList<PayDTO>()

    fun setItems(data: List<PayDTO>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayListHolder {
        val view = PayListItemLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return PayListHolder(view)
    }

    override fun onBindViewHolder(holder: PayListHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}