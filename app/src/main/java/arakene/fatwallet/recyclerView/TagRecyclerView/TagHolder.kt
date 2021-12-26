package arakene.fatwallet.recyclerView.TagRecyclerView

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import arakene.fatwallet.data.PayTag
import arakene.fatwallet.databinding.TagItemLayoutBinding

class TagHolder(private val binding: TagItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: PayTag) {
        binding.tagName.text = item.name
        binding.root.setOnClickListener {
            binding.tagViewModel!!.setTarget(item)
        }
        binding.tagDelete.setOnClickListener {
            binding.tagViewModel!!.deleteTag(item)
        }
        if (item.name == PayTag.MONTHLYOUTPUT){
            binding.tagDelete.visibility = View.INVISIBLE
        }
    }

}