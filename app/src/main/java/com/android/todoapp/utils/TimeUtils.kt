package com.android.todoapp.utils

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.widget.TimePicker
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

    val picker = TimePickerDialog(context, { _, selectedHour, selectedMinute ->
        listener.onTimeSet(null, selectedHour, selectedMinute)
    }, hour, minute, is24HourView)

    picker.setOnShowListener {
        val timePicker = picker.findViewById<TimePicker>(Resources.getSystem().getIdentifier("timePicker", "id", "android"))

        timePicker?.setOnTimeChangedListener { view, selectedHour, selectedMinute ->
            if (selectedHour < hour || (selectedHour == hour && selectedMinute < minute)) {
                timePicker.currentHour = hour
                timePicker.currentMinute = minute
            }
        }

        timePicker?.hour = hour
        timePicker?.minute = minute
    }

    return picker

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

fun formatDateToString(date: Date): String {
    val dateFormat = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault())
    return dateFormat.format(date)
}

fun convertDateToTimeString(date: Date): String {
    val outputFormat = SimpleDateFormat("dd-MM-yyyy h:mm a", Locale.getDefault())
    return outputFormat.format(date)
}

fun isDateToday(date: Date): Boolean {
    val today = Calendar.getInstance()
    val givenDate = Calendar.getInstance()
    givenDate.time = date

    return today.get(Calendar.YEAR) == givenDate.get(Calendar.YEAR) &&
            today.get(Calendar.MONTH) == givenDate.get(Calendar.MONTH) &&
            today.get(Calendar.DAY_OF_MONTH) == givenDate.get(Calendar.DAY_OF_MONTH)
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