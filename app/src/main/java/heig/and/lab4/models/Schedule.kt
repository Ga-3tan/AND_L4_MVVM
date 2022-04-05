package heig.and.lab4.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Schedule(
    @PrimaryKey(autoGenerate = true) var scheduleId : Long?,
    var ownerId : Long,
    var date : Calendar
)