package ru.antonc.budget.binding

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import ru.antonc.budget.R
import ru.antonc.budget.util.FORMAT_DECIMAL


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
//
//@BindingAdapter("imageResource")
//fun bindImageResource(view: ImageView, id: Int) {
//    view.setImageResource(id)
//}


@BindingAdapter("android:src")
fun setImageDrawable(view: ImageView, drawable: Drawable?) {
    view.setImageDrawable(drawable)
}

@BindingAdapter("android:src")
fun setImageResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}

@SuppressLint("SetTextI18n")
@BindingAdapter("android:textMoney")
fun textNumberFormatted(textView: TextView, value: Double) {
    textView.text = "${FORMAT_DECIMAL.format(value)} ₽"
}
