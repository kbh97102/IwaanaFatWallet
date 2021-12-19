package arakene.fatwallet.recyclerView.TagRecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import arakene.fatwallet.data.PayTag
import arakene.fatwallet.databinding.TagItemLayoutBinding
import arakene.fatwallet.test.TagList

class TagAdapter(private val model: TagList) : RecyclerView.Adapter<TagHolder>() {

    private val items = ArrayList<PayTag>()

    fun setItems(data: List<PayTag>) {
        items.clear()
        items.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagHolder {
        val view = TagItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        view.tagViewModel = model

        return TagHolder(view)
    }

    override fun onBindViewHolder(holder: TagHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}