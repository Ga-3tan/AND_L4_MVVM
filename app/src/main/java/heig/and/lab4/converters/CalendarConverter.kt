package heig.and.lab4.converters

import androidx.room.TypeConverter
import java.util.*

/**
 * Authors : Zwick Gaétan, Maziero Marco, Lamrani Soulaymane
 * Date : 30.04.2022
 */
class CalendarConverter {

    @TypeConverter
    fun toCalendar(dateLong: Long): Calendar = Calendar.getInstance().apply {
        time = Date(dateLong)
    }

    @TypeConverter
    fun fromCalendar(date: Calendar) = date.time.time
}