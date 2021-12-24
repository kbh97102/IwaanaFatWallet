package arakene.fatwallet.recyclerView.monthly

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import arakene.fatwallet.data.PayDTO
import arakene.fatwallet.databinding.MonthlyOutputItemLayoutBinding

class MonthlyAdapter : RecyclerView.Adapter<MonthlyHolder>() {

    private val items = ArrayList<PayDTO>()

    fun setData(data : List<PayDTO>){
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MonthlyHolder {
        val view = MonthlyOutputItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MonthlyHolder(view)
    }

    override fun onBindViewHolder(holder: MonthlyHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}