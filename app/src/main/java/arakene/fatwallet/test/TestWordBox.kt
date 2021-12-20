package arakene.fatwallet.test

import android.content.Context
import android.util.Log
import android.widget.Button
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.TextView

class TestWordBox(
    private val textView: TextView,
    private val wordBox: GridLayout,
    private val context: Context
) {

    fun addButton(name: String) {
        val currentTags = textView.text.toString().split(" ")

        if (currentTags.contains(name)) {
            return
        }

        val button = Button(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(15, 15, 0, 0)
            }
            text = name

            setOnClickListener {
                val builder = StringBuilder(textView.text)
                builder.append(" ").append(this.text)
                textView.text = builder.toString()
            }
        }

        wordBox.addView(button)
    }

}