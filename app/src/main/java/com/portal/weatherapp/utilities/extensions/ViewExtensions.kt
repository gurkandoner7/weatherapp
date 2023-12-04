package com.portal.weatherapp.utilities.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.portal.weatherapp.utilities.CustomTypefaceSpan

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Fragment.showKeyboard(view: View) {
    view.let { activity?.showKeyboard(it) }
}

fun Fragment.findNavControllerSafely(id: Int): NavController? {
    return if (findNavController().currentDestination?.id == id) {
        findNavController()
    } else {
        null
    }
}
fun Context.hideKeyboard(view: View) {
    val inputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.showKeyboard(view: View) {
    val inputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_FORCED)
}

fun View.showView(visible: Boolean) {
    when (visible) {
        true -> this.visibility = View.VISIBLE
        false -> this.visibility = View.GONE
    }
}

fun TextView.setChangedFontSpan(message: String, willChangeWord: String, font: Typeface) {
    val spannableText = SpannableString(message)
    val start = message.indexOf(willChangeWord)
    val end = message.indexOf(willChangeWord) + willChangeWord.length

    try {
        spannableText.setSpan(
            CustomTypefaceSpan(font),
            start,
            end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        text = spannableText
    } catch (e: java.lang.IndexOutOfBoundsException) {
        println("$willChangeWord was not found in TextView text")
    }
}

fun TextView.setChangedFontSpan(message: String, willChangeWords: Array<String>, font: Typeface) {
    val spannableText = SpannableString(message)

    willChangeWords.forEach { word ->
        val start = message.indexOf(word)
        val end = message.indexOf(word) + word.length

        try {
            spannableText.setSpan(
                CustomTypefaceSpan(font),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

        } catch (e: java.lang.IndexOutOfBoundsException) {
            println("$word was not found in TextView text")
        }
    }

    text = spannableText
}

fun TextView.setChangedFontSpan(message: String, vararg willChangeWords: Pair<String, Typeface>) {
    val spannableText = SpannableString(message)

    willChangeWords.forEach { (word, font) ->
        val start = message.indexOf(word)
        val end = message.indexOf(word) + word.length

        try {
            spannableText.setSpan(
                CustomTypefaceSpan(font),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )

        } catch (e: java.lang.IndexOutOfBoundsException) {
            println("$word was not found in TextView text")
        }
    }

    text = spannableText
}

@RequiresApi(Build.VERSION_CODES.Q)
fun TextView.withClickableSpan(
    colorResId: Int,
    clickablePart: String,
    isUnderlined: Boolean,
    isBoldText: Boolean,
    onClickListener: () -> Unit
) {
    val ss = SpannableString(text)
    val clickableSpan = object : ClickableSpan() {
        override fun updateDrawState(ds: TextPaint) {
            ds.color = ds.linkColor // you can use custom color
            ds.isUnderlineText = isUnderlined // this remove the underline
            ds.isFakeBoldText = isBoldText
        }

        override fun onClick(widget: View) {
            onClickListener.invoke()
        }
    }
    val clickablePartStart = text.indexOf(clickablePart)

    ss.setSpan(
        clickableSpan,
        clickablePartStart, clickablePartStart + clickablePart.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    ss.setSpan(
        ForegroundColorSpan(ContextCompat.getColor(context, colorResId)),
        clickablePartStart, clickablePartStart + clickablePart.length,
        Spanned.SPAN_INCLUSIVE_EXCLUSIVE
    )
    movementMethod = LinkMovementMethod.getInstance()
    setText(ss, TextView.BufferType.SPANNABLE)
    isClickable = true
}

fun TextView.setColoredSpan(colorResId: Int, isBoldText: Boolean, subText: String) {
    val ss = SpannableString(text)
    val subTextStart = text.indexOf(subText)
    ss.setSpan(
        ForegroundColorSpan(ContextCompat.getColor(context, colorResId)),
        subTextStart, subTextStart + subText.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE
    )
    if (isBoldText)
        ss.setSpan(
            StyleSpan(Typeface.BOLD),
            subTextStart, subTextStart + subText.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE
        )
    setText(ss, TextView.BufferType.SPANNABLE)
}







