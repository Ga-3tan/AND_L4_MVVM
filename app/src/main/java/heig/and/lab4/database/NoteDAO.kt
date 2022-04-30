package heig.and.lab4.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import heig.and.lab4.models.Note
import heig.and.lab4.models.NoteAndSchedule
import heig.and.lab4.models.Schedule

/**
 * Authors : Zwick Gaétan, Maziero Marco, Lamrani Soulaymane
 * Date : 30.04.2022
 */
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
    fun getAllNotes(): LiveData<List<NoteAndSchedule>>

    @Query("SELECT Note.* FROM Note INNER JOIN Schedule ON Note.noteId == Schedule.ownerId ORDER BY Schedule.date")
    fun getAllNotesByETA(): LiveData<List<NoteAndSchedule>>

    @Query("SELECT * FROM Note ORDER BY Note.creationDate")
    fun getALlNotesByCreationDate(): LiveData<List<NoteAndSchedule>>

    @Query("SELECT COUNT(*) FROM Note")
    fun getNotesCount(): LiveData<Int>

}