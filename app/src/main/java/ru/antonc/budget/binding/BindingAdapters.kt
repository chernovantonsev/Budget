package ru.antonc.budget.binding

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import ru.antonc.budget.R
import ru.antonc.budget.util.extenstions.afterTextChanged

@BindingAdapter("android:text", "android:spanText")
fun spanText(textView: TextView, text: String, spanText: String?) {
    if (spanText == null || spanText.isEmpty()) {
        textView.text = text
    } else {
        val spannable = SpannableString(text)

        var startIndex = text.indexOf(spanText, 0, true)

        while (startIndex != -1) {
            spannable.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(textView.context, R.color.colorPrimary)),
                startIndex,
                startIndex + spanText.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            startIndex = text.indexOf(spanText, startIndex + 1, true)
        }

        textView.text = spannable
    }
}

@BindingAdapter("android:isGone")
fun bindIsGone(view: View, isGone: Boolean?) {
    isGone?.let {
        view.visibility = if (isGone) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
}

@BindingAdapter("imageResource")
fun bindImageResource(view: ImageView, id: Int) {
    val t = R.drawable.ic_pie_chart
    view.setImageResource(id)
}