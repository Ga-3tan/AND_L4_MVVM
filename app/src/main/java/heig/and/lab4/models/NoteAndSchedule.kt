package heig.and.lab4.models

import androidx.room.Embedded
import androidx.room.Relation
import heig.and.lab4.models.Note
import heig.and.lab4.models.Schedule

data class NoteAndSchedule (
    @Embedded val note: Note,
    @Relation(
        parentColumn = "noteId",
        entityColumn = "ownerId"
    )
    val schedule: Schedule?
)
