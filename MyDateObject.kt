package com.example.myapplication

import java.util.*

class MyDateObject(
    var year: Int = 0,
    var month: Int = 0,
    var day: Int = 0,
    var hourOfDay: Int = 0,
    var minute: Int = 0
) {


    private fun timeAsString(hourOfDay: Int = this.hourOfDay, minute: Int = this.minute): String {
        var time = ""
        if (hourOfDay < 10)
            time += "0"
        time = "$time$hourOfDay:"

        if (minute < 10)
            time += "0"
        time += minute

        return time
    }


    private fun dateAsString(
        year: Int = this.year,
        month: Int = this.month,
        dayOfMonth: Int = this.day
    ): String {
        var date = "$year-"

        if (month < 10)
            date += "0"
        date = "$date$month-"

        if (dayOfMonth < 10)
            date += "0"

        return date + dayOfMonth

    }

    override fun toString(): String {
        return dateAsString() + " " + timeAsString()
    }

    fun getCurrentDate(): String {

        return MyDateObject(
            Calendar.getInstance().get(Calendar.YEAR),
            Calendar.getInstance().get(Calendar.MONTH),
            Calendar.getInstance().get(Calendar.DATE),
            Calendar.getInstance().get(Calendar.HOUR),
            Calendar.getInstance().get(Calendar.MINUTE)
        ).toString()

    }
}