package main

import java.util.*

data class Date(val year: Int, val month: Int, val day: Int): Comparable<Date>{
    //This constructor initializes the year, month and the day with the current date
    constructor(): this(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH)+1, Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
    override fun compareTo(other: Date): Int {
        if (this.year != other.year) {
            return this.year - other.year
        }
        if (this.month != other.month) {
            return this.month - other.month
        }
        return this.day - other.day
    }
}

fun Date.isLeapYear(): Boolean {
    return this.year % 4 == 0 && this.year % 100 != 0 || this.year % 400 == 0
}

fun Date.isThisValid(): Boolean {
    if (this.year <= 0 || this.month < 1 || this.month > 12 || this.day < 1 || this.day > 31) {
        return false
    }
    if (this.month == 2) {
        return if (this.isLeapYear()) this.day <= 29 else this.day <= 28
    }
    val daysInMonth = listOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
    return this.day <= daysInMonth[this.month - 1]
}