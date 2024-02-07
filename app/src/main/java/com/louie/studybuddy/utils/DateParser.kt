package com.louie.studybuddy.utils

object DateParser {
    private val months = arrayOf(
        "January", "February", "March", "April",
        "May", "June", "July",  "August",
        "September", "October", "November", "December"
    )

    fun parseDate(jsonDate: String): String {
        val dateParts = jsonDate.split("-")
        return months[dateParts[1].toInt() -1] + " " + dateParts[2] + ", " + dateParts[0]
    }
}