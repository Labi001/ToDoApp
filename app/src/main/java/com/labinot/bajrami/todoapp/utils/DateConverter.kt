package com.labinot.bajrami.todoapp.utils

import androidx.room.TypeConverter
import java.util.Date

class DateConverter {

    @TypeConverter
    fun timeTampFromDate(date: Date):Long {

        return date.time
    }

    @TypeConverter
    fun timeTampFromDate(timestamp: Long):Date? {

        return Date(timestamp)
    }

}