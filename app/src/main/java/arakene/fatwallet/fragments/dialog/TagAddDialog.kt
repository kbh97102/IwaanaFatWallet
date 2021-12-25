package arakene.fatwallet.fragments.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import arakene.fatwallet.databinding.TagAddLayoutBinding
import arakene.fatwallet.viewModel.TagViewModel

class TagAddDialog : DialogFragment() {

    private lateinit var binding: TagAddLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TagAddLayoutBinding.inflate(inflater, container, false)

        val model: TagViewModel by activityViewModels()

        binding.newTagAdd.setOnClickListener {
            model.addTag(binding.newTagName.text.toString())
            dismiss()
        }

        binding.tagCancel.setOnClickListener {
            dismiss()
        }

        return binding.root
    }


    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.BLACK))
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }
}