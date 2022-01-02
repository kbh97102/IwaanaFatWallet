package arakene.fatwallet.test

import android.content.Context
import android.view.View
import androidx.core.view.forEach
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class TagBoxController(
    private val tagChips: ChipGroup,
    private val tagBox: ChipGroup,
    private val context: Context
) {

    fun addButton(name: String) {
        val list = ArrayList<String>()
        tagChips.forEach {
            val c = it as Chip
            list.add(c.text.toString())
        }

        if (list.contains(name)) {
            return
        }

        val chip = Chip(context).apply {
            text = name
            isCloseIconVisible = true
            layoutDirection = View.LAYOUT_DIRECTION_LOCALE

            setOnClickListener {
                if (!isContains(name, tagChips)) {
                    tagBox.removeView(this)
                    tagChips.addView(this)
                }
            }
            setOnCloseIconClickListener {
                if (!canDelete(name)) {
                    tagChips.removeView(this)
                    tagBox.addView(this)
                }
            }
        }
        tagBox.addView(chip)
    }

    private fun isContains(name: String, group: ChipGroup): Boolean {
        val list = ArrayList<String>()
        group.forEach {
            val c = it as Chip
            list.add(c.text.toString())
        }
        return list.contains(name)
    }

    private fun canDelete(name: String): Boolean {
        return isContains(name, tagBox)
    }
}