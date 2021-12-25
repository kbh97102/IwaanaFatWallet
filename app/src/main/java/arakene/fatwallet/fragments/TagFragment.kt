package arakene.fatwallet.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import arakene.fatwallet.R
import arakene.fatwallet.databinding.TagLayoutBinding
import arakene.fatwallet.fragments.dialog.TagAddDialog
import arakene.fatwallet.recyclerView.TagRecyclerView.TagAdapter
import arakene.fatwallet.viewModel.TagViewModel

class TagFragment : Fragment() {

    private lateinit var binding: TagLayoutBinding
    private lateinit var tagAdapter: TagAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.tag_layout, container, false)

        setRecyclerView()

        binding.tagToolbar.setOnMenuItemClickListener {
            if (it.itemId == R.id.tag_add) {
                val dialog = TagAddDialog().show(
                    activity!!.supportFragmentManager,
                    null
                )
            }
            return@setOnMenuItemClickListener true

        }

        return binding.root
    }

    private fun setRecyclerView() {
        val model: TagViewModel by activityViewModels()

        tagAdapter = TagAdapter(model)
        binding.tagRecycler.apply {
            layoutManager = LinearLayoutManager(binding.root.context)
            adapter = tagAdapter
        }

        model.getTagList().observe(viewLifecycleOwner, {
            tagAdapter.setItems(it)
            tagAdapter.notifyDataSetChanged()
        })
    }

}