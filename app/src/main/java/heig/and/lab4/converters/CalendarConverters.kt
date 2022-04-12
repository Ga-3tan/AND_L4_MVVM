package heig.and.lab4.converters

import androidx.room.TypeConverter
import java.util.*

class CalendarConverters {

    @TypeConverter
    fun toCalendar(dateLong: Long) = Calendar.getInstance().apply {
        time = Date(dateLong)
    }

    @TypeConverter
    fun fromCalendar(date: Calendar) = date.time.time
}