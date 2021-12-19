package arakene.fatwallet.test

import android.content.Context
import android.widget.Button
import android.widget.LinearLayout
import arakene.fatwallet.databinding.PayAddLayoutBinding

class TestWordBox(private val binding: PayAddLayoutBinding, private val context: Context) {

    private var testNum = 0

    fun addButton(name : String) {
        val button = Button(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(15, 15, 0, 0)
            }
            text = name

            setOnClickListener {
                val builder = StringBuilder(binding.updateTags.text)
                builder.append(" ").append(this.text)
                binding.updateTags.text = builder.toString()
            }
        }

        binding.wordBox.addView(button)
    }

}