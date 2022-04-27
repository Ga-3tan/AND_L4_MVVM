package heig.and.lab4.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import heig.and.lab4.models.Note
import heig.and.lab4.models.NoteAndSchedule
import heig.and.lab4.models.Schedule

@Dao
interface NoteDAO {

    @Insert
    fun insert(note: Note): Long

    @Insert
    fun insert(schedule: Schedule)

    @Insert
    fun insertAll(vararg notes: Note)

    @Insert
    fun insertAll(vararg schedule: Schedule)

    @Query("DELETE FROM Note")
    fun deleteAllNotes()

    @Query("DELETE FROM Schedule")
    fun deleteAllSchedule()

    @Query("SELECT * FROM Note")
    fun getALlNotes(): LiveData<List<NoteAndSchedule>>

    @Query("SELECT COUNT(*) FROM Note")
    fun getNotesCount(): LiveData<Int>

}