package com.android.todoapp.utils

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StrikethroughSpan
import android.text.style.StyleSpan
import android.widget.TextView

fun TextView.setStrikeThroughText(text: String) {
    val spannableString = SpannableString(text)
    spannableString.setSpan(
        StrikethroughSpan(),
        0,
        text.length,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    this.text = spannableString
    this.paintFlags = this.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
}


fun TextView.setStrikeThroughAndBoldText(text: String) {
    val spannableString = SpannableString(text)

    val strikethroughSpan = StrikethroughSpan()
    spannableString.setSpan(strikethroughSpan, 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

    val boldSpan = StyleSpan(Typeface.BOLD)
    spannableString.setSpan(boldSpan, 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

    this.text = spannableString
    this.paintFlags = this.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
}

fun TextView.removeStrikeThroughAndBoldText() {
    val spannableString = SpannableString(text)

    val strikethroughSpans = spannableString.getSpans(
        0,
        spannableString.length,
        StrikethroughSpan::class.java
    )

    for (span in strikethroughSpans) {
        spannableString.removeSpan(span)
    }

    val boldSpans = spannableString.getSpans(
        0,
        spannableString.length,
        StyleSpan::class.java
    )

    for (span in boldSpans) {
        if (span.style == Typeface.BOLD) {
            spannableString.removeSpan(span)
        }
    }

    this.text = spannableString
    this.paintFlags = this.paintFlags and (Paint.STRIKE_THRU_TEXT_FLAG.inv())
}

fun TextView.setRedBoldText(text: String) {
    val spannableString = SpannableString(text)
    spannableString.setSpan(ForegroundColorSpan(Color.RED), 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    spannableString.setSpan(StyleSpan(Typeface.BOLD), 0, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    this.text = spannableString
}