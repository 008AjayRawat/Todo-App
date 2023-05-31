package com.android.todoapp.utils

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import java.text.SimpleDateFormat
import java.util.*


const val AM = "AM"
const val PM = "PM"

fun getDatePickerDialog(context: Context, initialDate: Calendar, onDateSetListener: DatePickerDialog.OnDateSetListener): DatePickerDialog {
    val year = initialDate.get(Calendar.YEAR)
    val month = initialDate.get(Calendar.MONTH)
    val day = initialDate.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(context, onDateSetListener, year, month, day)
    // Set minimum date as current date
    val currentDate = Calendar.getInstance()
    datePickerDialog.datePicker.minDate = currentDate.timeInMillis

    return datePickerDialog
}

fun getTimePickerDialog(
    context: Context,
    listener: TimePickerDialog.OnTimeSetListener,
    is24HourView: Boolean = false
): TimePickerDialog {

    val currentTime = Calendar.getInstance()
    val hour = currentTime.get(Calendar.HOUR_OF_DAY)
    val minute = currentTime.get(Calendar.MINUTE)

    return TimePickerDialog(context, listener, hour, minute, is24HourView)

}

fun convert24To12(hourMinute: String): Pair<String, String>? {
    return try {
        val inputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val outputFormat = SimpleDateFormat("h:mm", Locale.getDefault())
        val amPmFormat = SimpleDateFormat("a", Locale.getDefault())
        inputFormat.parse(hourMinute)?.let {
            val formattedTime = outputFormat.format(it)
            val amPm = amPmFormat.format(it)
            Pair(formattedTime, amPm)
        }
    } catch (e: Exception) {
        null
    }
}

fun convertTimePairToDate(timePair: Pair<String, String>): Date? {
    val currentDate = Calendar.getInstance().time
    val formattedTime = "${currentDate.toDateString()} ${timePair.first} ${timePair.second}"
    val outputFormat = SimpleDateFormat("yyyy-MM-dd h:mm a", Locale.getDefault())
    return try {
        outputFormat.parse(formattedTime)
    } catch (e: Exception) {
        null
    }
}

fun Date.toDateString(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return dateFormat.format(this)
}

fun convertStringToDate(dateString: String): Date? {
    val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return try {
        dateFormat.parse(dateString)
    } catch (e: Exception) {
        null
    }
}

fun convertDateToTimeString(date: Date): String {
    val outputFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
    return outputFormat.format(date)
}

fun isDateInPast(date: Date): Boolean {
    val currentDate = Calendar.getInstance().time
    return date.before(currentDate)
}

fun mergeDateTime(date: Date, time: Date): Date {
    val calendarTime = Calendar.getInstance()
    calendarTime.time = time

    val calendarDate = Calendar.getInstance()
    calendarDate.time = date

    val mergedCalendar = Calendar.getInstance()
    mergedCalendar.set(
        calendarDate.get(Calendar.YEAR),
        calendarDate.get(Calendar.MONTH),
        calendarDate.get(Calendar.DAY_OF_MONTH),
        calendarTime.get(Calendar.HOUR_OF_DAY),
        calendarTime.get(Calendar.MINUTE),
        calendarTime.get(Calendar.SECOND)
    )

    return mergedCalendar.time
}